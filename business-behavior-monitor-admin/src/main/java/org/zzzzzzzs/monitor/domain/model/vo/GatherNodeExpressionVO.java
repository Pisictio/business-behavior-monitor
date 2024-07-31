package org.zzzzzzzs.monitor.domain.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zzs
 * @description
 * @create 2024/7/30 14:36
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GatherNodeExpressionVO {
    // 监控对象的ID，用于标识监控的是哪个对象
    private String monitorId;
    // 监控节点的ID，用于唯一标识监控节点
    private String monitorNodeId;
    // 监控节点所属的系统名称
    private String gatherSystemName;
    // 监控节点所属的类名
    private String gatherClazzName;
    // 监控节点的具体方法名
    private String gatherMethodName;
    // 监控节点的属性列表
    private List<Field> fields;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Field {
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
    }
}
