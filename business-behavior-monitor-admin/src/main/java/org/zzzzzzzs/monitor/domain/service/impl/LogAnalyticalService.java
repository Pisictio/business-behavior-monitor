package org.zzzzzzzs.monitor.domain.service.impl;

import com.alibaba.fastjson.JSONObject;
import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;
import org.springframework.stereotype.Service;
import org.zzzzzzzs.monitor.domain.model.entity.MonitorDataEntity;
import org.zzzzzzzs.monitor.domain.model.vo.GatherNodeExpressionVO;
import org.zzzzzzzs.monitor.domain.repository.IMonitorRepository;
import org.zzzzzzzs.monitor.domain.service.ILogAnalyticalService;
import org.zzzzzzzs.monitor.types.Constants;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zzs
 * @description
 * @create 2024/7/30 14:24
 */
@Service
public class LogAnalyticalService implements ILogAnalyticalService {

    @Resource
    private IMonitorRepository repository;

    @Override
    public void doAnalytical(String systemName, String className, String methodName, List<String> logList) throws OgnlException {
        // 查询监控节点和对应的日志属性
        List<GatherNodeExpressionVO> gatherNodeExpressionVOList = repository.queryGatherNodeExpressionVO(systemName, className, methodName);
        if (gatherNodeExpressionVOList == null || gatherNodeExpressionVOList.isEmpty()) {
            return;
        }
        for (GatherNodeExpressionVO gatherNodeExpressionVO : gatherNodeExpressionVOList) {
            // 查询监控名称
            String monitorName = repository.queryMonitorName(gatherNodeExpressionVO.getMonitorId());
            // 解析日志
            List<GatherNodeExpressionVO.Field> fields = gatherNodeExpressionVO.getFields();
            for (GatherNodeExpressionVO.Field field : fields) {
                Integer logIndex = field.getLogIndex();
                String logName = logList.get(0);
                if (!logName.equals(field.getLogName())) {
                    continue;
                }
                String attributeValue = "";
                // 从日志中获取当前索引的日志字符串
                String logStr = logList.get(logIndex);
                // 判断日志的类型是否为"Object"
                if ("Object".equals(field.getLogType())) {
                    // 创建一个Ognl上下文，并将日志字符串解析为JSONObject作为根对象
                    OgnlContext ognlContext = new OgnlContext();
                    ognlContext.setRoot(JSONObject.parseObject(logStr));
                    Object root = ognlContext.getRoot();
                    // 通过Ognl表达式获取字段的值，并转换为字符串形式
                    attributeValue = String.valueOf(Ognl.getValue(field.getAttributeOgnl(), ognlContext, root));
                } else {
                    // 对于非Object类型的日志，首先去除字符串两端的空格
                    attributeValue = logStr.trim();
                    // 如果字符串中包含冒号，则提取冒号后面的部分，并再次去除空格
                    if (attributeValue.contains(Constants.COLON)) {
                        attributeValue = attributeValue.split(Constants.COLON)[1].trim();
                    }
                }
                MonitorDataEntity monitorDataEntity = MonitorDataEntity.builder()
                        .monitorId(gatherNodeExpressionVO.getMonitorId())
                        .monitorName(monitorName)
                        .monitorNodeId(gatherNodeExpressionVO.getMonitorNodeId())
                        .systemName(gatherNodeExpressionVO.getGatherSystemName())
                        .clazzName(gatherNodeExpressionVO.getGatherClazzName())
                        .methodName(gatherNodeExpressionVO.getGatherMethodName())
                        .attributeName(field.getAttributeName())
                        .attributeField(field.getAttributeField())
                        .attributeValue(attributeValue)
                        .build();
                // 保存监控数据
                repository.saveMonitorData(monitorDataEntity);
            }
        }
    }
}
