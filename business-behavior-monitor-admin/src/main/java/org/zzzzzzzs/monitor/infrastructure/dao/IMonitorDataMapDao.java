package org.zzzzzzzs.monitor.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import org.zzzzzzzs.monitor.infrastructure.po.MonitorDataMap;

import java.util.List;

/**
 * @author zzs
 * @description
 * @create 2024/7/29 21:57
 */
@Mapper
public interface IMonitorDataMapDao {
    /**
     * 查询监控数据Map
     *
     * @return 监控数据Map
     */
    List<MonitorDataMap> queryMonitorDataMapList();
    /**
     * 查询监控名称
     *
     * @param monitorId 监控id
     * @return 监控名称
     */
    String queryMonitorName(String monitorId);
}
