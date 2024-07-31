package org.zzzzzzzs.monitor.infrastructure.po;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zzs
 * @description 监控数据Map节点
 * @create 2024/7/28 10:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonitorDataMapNode {

    private Long id;
    // 监控对象的ID，用于标识监控的是哪个对象
    private String monitorId;
    // 监控节点的ID，用于唯一标识监控节点
    private String monitorNodeId;
    // 监控节点的名称
    private String monitorNodeName;
    // 监控节点所属的系统名称
    private String gatherSystemName;
    // 监控节点所属的类名
    private String gatherClazzName;
    // 监控节点的具体方法名
    private String gatherMethodName;
    // 监控节点的位置信息，例如文件路径
    private String loc;
    // 监控节点的颜色标识，用于可视化展示
    private String color;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;
}
