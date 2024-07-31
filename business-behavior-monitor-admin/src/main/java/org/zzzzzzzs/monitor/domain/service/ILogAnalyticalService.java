package org.zzzzzzzs.monitor.domain.service;

import ognl.OgnlException;

import java.util.List;

/**
 * @author zzs
 * @description 日志解析服务
 * @create 2024/7/30 13:30
 */
public interface ILogAnalyticalService {

    void doAnalytical(String systemName, String className, String methodName, List<String> logList) throws OgnlException;
}
