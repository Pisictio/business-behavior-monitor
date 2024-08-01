package org.zzzzzzzs.monitor.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import org.zzzzzzzs.monitor.infrastructure.po.MonitorDataMapNodeLink;

import java.util.List;

/**
 * @author zzs
 * @description
 * @create 2024/7/29 21:58
 */
@Mapper
public interface IMonitorDataMapNodeLinkDao {
    /**
     * 查询节点关系配置
     *
     * @param monitorId 监控ID
     * @return 节点关系配置
     */
    List<MonitorDataMapNodeLink> queryMonitorDataMapNodeLinkConfig(String monitorId);
}
