package org.zzzzzzzs.monitor.infrastructure.po;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zzs
 * @description 监控数据Map节点连线
 * @create 2024/7/28 10:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonitorDataMapNodeLink {

    private Long id;
    // 监控ID
    private String monitorId;
    // 监控from节点ID
    private String fromMonitorNodeId;
    // 监控to节点ID
    private String toMonitorNodeId;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;
}
