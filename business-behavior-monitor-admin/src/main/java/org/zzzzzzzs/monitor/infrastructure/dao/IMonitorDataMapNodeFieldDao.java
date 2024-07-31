package org.zzzzzzzs.monitor.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import org.zzzzzzzs.monitor.infrastructure.po.MonitorDataMapNodeField;

import java.util.List;

/**
 * @author zzs
 * @description
 * @create 2024/7/29 21:58
 */
@Mapper
public interface IMonitorDataMapNodeFieldDao {
    /**
     * 查询节点字段
     *
     * @param monitorNodeId 节点id
     * @return 节点字段
     */
    List<MonitorDataMapNodeField> queryMonitorDataMapNodeFieldList(String monitorNodeId);
}
