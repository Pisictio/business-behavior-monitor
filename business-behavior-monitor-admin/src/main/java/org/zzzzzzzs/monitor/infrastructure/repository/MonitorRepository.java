package org.zzzzzzzs.monitor.infrastructure.repository;

import org.springframework.stereotype.Repository;
import org.zzzzzzzs.monitor.domain.model.entity.MonitorDataEntity;
import org.zzzzzzzs.monitor.domain.model.entity.MonitorDataMapEntity;
import org.zzzzzzzs.monitor.domain.model.vo.GatherNodeExpressionVO;
import org.zzzzzzzs.monitor.domain.model.vo.MonitorTreeConfigVO;
import org.zzzzzzzs.monitor.domain.repository.IMonitorRepository;
import org.zzzzzzzs.monitor.infrastructure.dao.*;
import org.zzzzzzzs.monitor.infrastructure.po.*;
import org.zzzzzzzs.monitor.infrastructure.redis.IRedisService;
import org.zzzzzzzs.monitor.types.Constants;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zzs
 * @description 监控仓储
 * @create 2024/7/30 15:25
 */
@Repository
public class MonitorRepository implements IMonitorRepository {

    @Resource
    private IMonitorDataDao monitorDataDao;
    @Resource
    private IMonitorDataMapDao monitorDataMapDao;
    @Resource
    private IMonitorDataMapNodeDao monitorDataMapNodeDao;
    @Resource
    private IMonitorDataMapNodeFieldDao monitorDataMapNodeFieldDao;
    @Resource
    private IMonitorDataMapNodeLinkDao monitorDataMapNodeLinkDao;
    @Resource
    private IRedisService redisService;

    @Override
    public List<GatherNodeExpressionVO> queryGatherNodeExpressionVO(String systemName, String className, String methodName) {
        List<GatherNodeExpressionVO> gatherNodeExpressionVOList = new ArrayList<>();

        MonitorDataMapNode monitorDataMapNodeReq = new MonitorDataMapNode();
        monitorDataMapNodeReq.setGatherSystemName(systemName);
        monitorDataMapNodeReq.setGatherClazzName(className);
        monitorDataMapNodeReq.setGatherMethodName(methodName);
        // 查询监控节点
        List<MonitorDataMapNode> monitorDataMapNodeList = monitorDataMapNodeDao.queryMonitorDataMapNodeList(monitorDataMapNodeReq);
        if (monitorDataMapNodeList.isEmpty()) {
            return null;
        }

        for (MonitorDataMapNode monitorDataMapNode : monitorDataMapNodeList) {
            // 查询监控节点id
            String monitorNodeId = monitorDataMapNode.getMonitorNodeId();
            // 根据监控节点id查询监控节点日志属性
            List<MonitorDataMapNodeField> monitorDataMapNodeFieldList = monitorDataMapNodeFieldDao.queryMonitorDataMapNodeFieldList(monitorNodeId);
            List<GatherNodeExpressionVO.Field> fields = new ArrayList<>();
            for (MonitorDataMapNodeField monitorDataMapNodeField : monitorDataMapNodeFieldList) {
                GatherNodeExpressionVO.Field field = GatherNodeExpressionVO.Field.builder()
                        .attributeField(monitorDataMapNodeField.getAttributeField())
                        .attributeName(monitorDataMapNodeField.getAttributeName())
                        .attributeOgnl(monitorDataMapNodeField.getAttributeOgnl())
                        .logIndex(monitorDataMapNodeField.getLogIndex())
                        .logName(monitorDataMapNodeField.getLogName())
                        .logType(monitorDataMapNodeField.getLogType())
                        .build();
                fields.add(field);
            }
            GatherNodeExpressionVO gatherNodeExpressionVO = GatherNodeExpressionVO.builder()
                    .monitorId(monitorDataMapNode.getMonitorId())
                    .monitorNodeId(monitorDataMapNode.getMonitorNodeId())
                    .gatherSystemName(monitorDataMapNode.getGatherSystemName())
                    .gatherClazzName(monitorDataMapNode.getGatherClazzName())
                    .gatherMethodName(monitorDataMapNode.getGatherMethodName())
                    .fields(fields)
                    .build();
            gatherNodeExpressionVOList.add(gatherNodeExpressionVO);
        }
        return gatherNodeExpressionVOList;
    }

    @Override
    public String queryMonitorName(String monitorId) {
        return monitorDataMapDao.queryMonitorName(monitorId);
    }

    @Override
    public void saveMonitorData(MonitorDataEntity monitorDataEntity) {
        MonitorData monitorData = MonitorData.builder()
                .monitorId(monitorDataEntity.getMonitorId())
                .monitorName(monitorDataEntity.getMonitorName())
                .monitorNodeId(monitorDataEntity.getMonitorNodeId())
                .systemName(monitorDataEntity.getSystemName())
                .clazzName(monitorDataEntity.getClazzName())
                .methodName(monitorDataEntity.getMethodName())
                .attributeName(monitorDataEntity.getAttributeName())
                .attributeField(monitorDataEntity.getAttributeField())
                .attributeValue(monitorDataEntity.getAttributeValue())
                .build();
        String cacheKey = Constants.RedisKey.MONITOR_NODE_DATA_COUNT_KEY + monitorDataEntity.getMonitorId() + Constants.UNDERLINE + monitorDataEntity.getMonitorNodeId();
        if (!redisService.isExists(cacheKey)) {
            redisService.setAtomicLong(cacheKey, 1);
        } else {
            redisService.incr(cacheKey);
        }
        monitorDataDao.saveMonitorData(monitorData);
    }

    @Override
    public List<MonitorDataMapEntity> queryMonitorDataMap() {
        List<MonitorDataMapEntity> monitorDataMapEntityList = new ArrayList<>();
        List<MonitorDataMap> monitorDataMapList = monitorDataMapDao.queryMonitorDataMapList();
        for (MonitorDataMap monitorDataMap : monitorDataMapList) {
            MonitorDataMapEntity monitorDataMapEntity = MonitorDataMapEntity.builder()
                    .monitorId(monitorDataMap.getMonitorId())
                    .monitorName(monitorDataMap.getMonitorName())
                    .build();
            monitorDataMapEntityList.add(monitorDataMapEntity);
        }
        return monitorDataMapEntityList;
    }

    @Override
    public MonitorTreeConfigVO queryMonitorFlowMap(String monitorId) {
        // 查询监控节点
        List<MonitorDataMapNode> monitorDataMapNodeList = monitorDataMapNodeDao.queryMonitorDataMapNodeConfig(monitorId);
        // 查询监控节点连线
        List<MonitorDataMapNodeLink> monitorDataMapNodeLinkList = monitorDataMapNodeLinkDao.queryMonitorDataMapNodeLinkConfig(monitorId);
        // 根据from分组
        Map<String, List<String>> collect = monitorDataMapNodeLinkList.stream()
                .collect(Collectors.groupingBy(MonitorDataMapNodeLink::getFromMonitorNodeId,
                        Collectors.mapping(MonitorDataMapNodeLink::getToMonitorNodeId,
                                Collectors.toList())));

        // 构建节点
        List<MonitorTreeConfigVO.Node> nodeList = new ArrayList<>();
        for (MonitorDataMapNode monitorDataMapNode : monitorDataMapNodeList) {
            String cache = Constants.RedisKey.MONITOR_NODE_DATA_COUNT_KEY + monitorId + Constants.UNDERLINE + monitorDataMapNode.getMonitorNodeId();
            // 获取节点数据
            Long count = redisService.getAtomicLong(cache);
            MonitorTreeConfigVO.Node node = MonitorTreeConfigVO.Node.builder()
                    .monitorNodeId(monitorDataMapNode.getMonitorNodeId())
                    .monitorNodeName(monitorDataMapNode.getMonitorNodeName())
                    .monitorNodeValue(null == count ? "0" : String.valueOf(count))
                    .loc(monitorDataMapNode.getLoc())
                    .color(monitorDataMapNode.getColor())
                    .build();
            nodeList.add(node);
        }

        // 构建连线
        List<MonitorTreeConfigVO.Link> linkList = new ArrayList<>();
        for (MonitorDataMapNodeLink monitorDataMapNodeLink : monitorDataMapNodeLinkList) {
            String fromCacheKey = Constants.RedisKey.MONITOR_NODE_DATA_COUNT_KEY + monitorId + Constants.UNDERLINE + monitorDataMapNodeLink.getFromMonitorNodeId();
            // from的流量值
            Long fromCount = redisService.getAtomicLong(fromCacheKey);
            Long toCount = 0L;
            List<String> toNodeIdList = collect.get(monitorDataMapNodeLink.getFromMonitorNodeId());
            for (String toNodeId : toNodeIdList) {
                String toCacheKey = Constants.RedisKey.MONITOR_NODE_DATA_COUNT_KEY + monitorId + Constants.UNDERLINE + toNodeId;
                toCount += redisService.getAtomicLong(toCacheKey);
            }

            // 计算未到达的流量值（from的流量-to的流量）
            long linkValue = (null == fromCount ? 0L : fromCount) - toCount;

            MonitorTreeConfigVO.Link link = MonitorTreeConfigVO.Link.builder()
                    .fromMonitorNodeId(monitorDataMapNodeLink.getFromMonitorNodeId())
                    .toMonitorNodeId(monitorDataMapNodeLink.getToMonitorNodeId())
                    .nodeLinkKey(String.valueOf(monitorDataMapNodeLink.getId()))
                    .linkValue(String.valueOf(linkValue <= 0 ? 0 : linkValue))
                    .build();
            linkList.add(link);
        }

        return MonitorTreeConfigVO.builder()
                .monitorId(monitorId)
                .nodeList(nodeList)
                .linkList(linkList)
                .build();
    }
}
