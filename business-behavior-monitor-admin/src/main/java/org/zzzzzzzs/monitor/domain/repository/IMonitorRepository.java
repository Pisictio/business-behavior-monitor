package org.zzzzzzzs.monitor.domain.repository;

import org.zzzzzzzs.monitor.domain.model.entity.MonitorDataEntity;
import org.zzzzzzzs.monitor.domain.model.entity.MonitorDataMapEntity;
import org.zzzzzzzs.monitor.domain.model.vo.GatherNodeExpressionVO;

import java.util.List;

/**
 * @author zzs
 * @description
 * @create 2024/7/30 13:31
 */
public interface IMonitorRepository {

    /**
     * 查询采集节点表达式
     *
     * @param systemName 系统名称
     * @param className  类名
     * @param methodName 方法名
     * @return 采集节点表达式
     */
    List<GatherNodeExpressionVO> queryGatherNodeExpressionVO(String systemName, String className, String methodName);

    /**
     * 查询监控名称
     *
     * @param monitorId 监控id
     * @return 监控名称
     */
    String queryMonitorName(String monitorId);

    /**
     * 保存监控数据
     *
     * @param monitorDataEntity 监控数据
     */
    void saveMonitorData(MonitorDataEntity monitorDataEntity);

    /**
     * 查询监控数据
     *
     * @return 监控数据
     */
    List<MonitorDataMapEntity> queryMonitorDataMap();
}
