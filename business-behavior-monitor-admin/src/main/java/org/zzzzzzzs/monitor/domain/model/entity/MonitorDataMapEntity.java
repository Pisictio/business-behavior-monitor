package org.zzzzzzzs.monitor.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zzs
 * @description 监控数据Map实体
 * @create 2024/7/28 10:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonitorDataMapEntity {

    // 监控对象的ID
    private String monitorId;
    // 监控对象的名称
    private String monitorName;
}
