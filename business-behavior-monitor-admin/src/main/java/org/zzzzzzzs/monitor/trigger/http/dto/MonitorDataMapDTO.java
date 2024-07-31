package org.zzzzzzzs.monitor.trigger.http.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zzs
 * @description
 * @create 2024/7/31 15:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonitorDataMapDTO {
    // 监控对象的ID
    private String monitorId;
    // 监控对象的名称
    private String monitorName;
}
