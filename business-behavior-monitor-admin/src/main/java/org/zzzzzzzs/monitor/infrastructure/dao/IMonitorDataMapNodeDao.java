package org.zzzzzzzs.monitor.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import org.zzzzzzzs.monitor.infrastructure.po.MonitorDataMapNode;

import java.util.List;

/**
 * @author zzs
 * @description
 * @create 2024/7/29 21:58
 */
@Mapper
public interface IMonitorDataMapNodeDao {
    /**
     * 查询节点列表
     *
     * @param monitorDataMapNode 查询条件
     * @return 节点列表
     */
    List<MonitorDataMapNode> queryMonitorDataMapNodeList(MonitorDataMapNode monitorDataMapNode);

    /**
     * 查询节点配置
     *
     * @param monitorId 监控ID
     * @return 节点配置
     */
    List<MonitorDataMapNode> queryMonitorDataMapNodeConfig(String monitorId);

    /**
     * 更新节点配置
     *
     * @param monitorDataMapNode 节点配置
     * @return
     */
    int updateMonitorDataMapNodeConfig(MonitorDataMapNode monitorDataMapNode);
}
