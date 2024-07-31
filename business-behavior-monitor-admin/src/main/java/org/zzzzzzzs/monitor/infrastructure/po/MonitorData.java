package org.zzzzzzzs.monitor.infrastructure.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zzs
 * @description 监控数据
 * @create 2024/7/28 10:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonitorData {

    private Long id;
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
    // 实体的创建时间
    private Date createTime;
    // 实体的更新时间
    private Date updateTime;
}
