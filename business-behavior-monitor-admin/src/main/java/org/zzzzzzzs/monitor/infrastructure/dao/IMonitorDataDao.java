package org.zzzzzzzs.monitor.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import org.zzzzzzzs.monitor.domain.model.entity.MonitorDataEntity;
import org.zzzzzzzs.monitor.infrastructure.po.MonitorData;

import java.util.List;

/**
 * @author zzs
 * @description
 * @create 2024/7/29 21:57
 */
@Mapper
public interface IMonitorDataDao {

    List<MonitorData> queryMonitorDataList(MonitorData monitorData);

    /**
     * 保存监控数据
     *
     * @param monitorData 监控数据
     */
    void saveMonitorData(MonitorData monitorData);
}
