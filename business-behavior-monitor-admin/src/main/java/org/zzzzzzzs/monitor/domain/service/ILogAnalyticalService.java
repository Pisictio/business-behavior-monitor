package org.zzzzzzzs.monitor.domain.service;

import ognl.OgnlException;
import org.zzzzzzzs.monitor.domain.model.entity.MonitorDataMapEntity;

import java.util.List;

/**
 * @author zzs
 * @description 日志解析服务
 * @create 2024/7/30 13:30
 */
public interface ILogAnalyticalService {

    /**
     * 日志解析
     *
     * @param systemName 系统名称
     * @param className  类名
     * @param methodName 方法名
     * @param logList    日志信息
     * @throws OgnlException ognl异常
     */
    void doAnalytical(String systemName, String className, String methodName, List<String> logList) throws OgnlException;

    /**
     * 查询监控数据
     *
     * @return 监控数据
     */
    List<MonitorDataMapEntity> queryMonitorDataMap();
}
