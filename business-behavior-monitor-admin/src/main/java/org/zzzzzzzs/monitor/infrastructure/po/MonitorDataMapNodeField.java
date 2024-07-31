package org.zzzzzzzs.monitor.infrastructure.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zzs
 * @description 监控数据Map节点字段
 * @create 2024/7/28 10:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonitorDataMapNodeField {

    private Long id;
    // 监控对象的ID
    private String monitorId;
    // 监控节点的ID，用于标识日志产生的具体节点
    private String monitorNodeId;
    // 日志的名称，用于描述日志的类型或主题
    private String logName;
    // 日志的索引号，用于序列化日志数据
    private Integer logIndex;
    // 日志的类型，用于分类日志
    private String logType;
    // 属性的名称，当日志与特定属性相关时使用
    private String attributeName;
    // 属性的字段，详细描述属性的组成部分
    private String attributeField;
    // 属性的OGNL表达式，用于动态获取属性值
    private String attributeOgnl;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;
}
