package org.zzzzzzzs.monitor.infrastructure.repository;

import org.springframework.stereotype.Repository;
import org.zzzzzzzs.monitor.domain.model.entity.MonitorDataEntity;
import org.zzzzzzzs.monitor.domain.model.vo.GatherNodeExpressionVO;
import org.zzzzzzzs.monitor.domain.repository.IMonitorRepository;
import org.zzzzzzzs.monitor.infrastructure.dao.IMonitorDataDao;
import org.zzzzzzzs.monitor.infrastructure.dao.IMonitorDataMapDao;
import org.zzzzzzzs.monitor.infrastructure.dao.IMonitorDataMapNodeDao;
import org.zzzzzzzs.monitor.infrastructure.dao.IMonitorDataMapNodeFieldDao;
import org.zzzzzzzs.monitor.infrastructure.po.MonitorData;
import org.zzzzzzzs.monitor.infrastructure.po.MonitorDataMapNode;
import org.zzzzzzzs.monitor.infrastructure.po.MonitorDataMapNodeField;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<GatherNodeExpressionVO> queryGatherNodeExpressionVO(String systemName, String className, String methodName) {
        List<GatherNodeExpressionVO> gatherNodeExpressionVOList = new ArrayList<>();

        MonitorDataMapNode monitorDataMapNodeReq = new MonitorDataMapNode();
        monitorDataMapNodeReq.setGatherSystemName(systemName);
        monitorDataMapNodeReq.setGatherClazzName(className);
        monitorDataMapNodeReq.setGatherMethodName(methodName);
        // 查询监控节点
        List<MonitorDataMapNode> monitorDataMapNodeList = monitorDataMapNodeDao.queryMonitorDataMapNodeList(monitorDataMapNodeReq);
        if (monitorDataMapNodeList.isEmpty()){
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
        monitorDataDao.saveMonitorData(monitorData);
    }
}
