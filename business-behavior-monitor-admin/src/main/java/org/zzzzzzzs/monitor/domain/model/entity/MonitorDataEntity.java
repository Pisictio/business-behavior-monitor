package org.zzzzzzzs.monitor.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zzs
 * @description
 * @create 2024/7/30 14:52
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MonitorDataEntity {
    // 监控对象的ID
    private String monitorId;
    // 监控对象的名称
    private String monitorName;
    // 监控节点的ID
    private String monitorNodeId;
    // 系统的名称
    private String systemName;
    // 监控对象所在的类名
    private String clazzName;
    // 监控的方法名
    private String methodName;
    // 监控的属性名
    private String attributeName;
    // 属性对应的字段名
    private String attributeField;
    // 属性的值
    private String attributeValue;
}
