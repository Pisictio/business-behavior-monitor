package org.zzzzzzzs.monitor.infrastructure.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zzs
 * @description 监控数据Map
 * @create 2024/7/28 10:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonitorDataMap {

    private Long id;
    // 监控对象的ID
    private String monitorId;
    // 监控对象的名称
    private String monitorName;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;

}
