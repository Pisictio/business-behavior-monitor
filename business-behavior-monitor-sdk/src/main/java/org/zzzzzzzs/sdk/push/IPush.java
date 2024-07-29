package org.zzzzzzzs.sdk.push;

import org.zzzzzzzs.sdk.model.LogMessage;

/**
 * @author zzs
 * @description 日志推送
 * @create 2024/7/28 14:13
 */
public interface IPush {

    void open(String host, int port);

    void push(LogMessage logMessage);
}
