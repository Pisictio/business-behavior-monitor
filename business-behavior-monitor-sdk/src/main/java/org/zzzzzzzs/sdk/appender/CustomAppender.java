package org.zzzzzzzs.sdk.appender;


import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import org.zzzzzzzs.sdk.model.LogMessage;
import org.zzzzzzzs.sdk.push.IPush;
import org.zzzzzzzs.sdk.push.impl.RedisPush;

import java.util.Arrays;

/**
 * @author zzs
 * @description 自定义日志采集
 * @create 2024/7/28 13:08
 */
public class CustomAppender<E> extends AppenderBase<E> {

    /**
     * 系统名称，用于标识日志所属的系统。
     */
    private String systemName;

    /**
     * 组名，用于标识日志所属的组别，用于过滤日志。
     */
    private String groupId;

    /**
     * Redis服务器的主机地址。
     */
    private String host;

    /**
     * Redis服务器的端口号。
     */
    private Integer port;

    /**
     * Redis推送实例，用于将日志推送到Redis中。
     */
    private final IPush redisPush = new RedisPush();

    /**
     * 将事件对象追加到日志系统中。
     * 此方法主要用于处理日志事件，将日志事件转换为LogMessage，并通过RedisPush推送到Redis中。
     *
     * @param eventObject 日志事件对象，需要将其转换为日志消息并推送。
     */
    @Override
    protected void append(E eventObject) {
        // 初始化Redis推送，打开与Redis的连接。
        redisPush.open(host, port);

        // 判断事件对象是否为ILoggingEvent实例，是则进行日志处理。
        if (eventObject instanceof ILoggingEvent) {
            ILoggingEvent event = (ILoggingEvent) eventObject;

            // 初始化方法名和类名，用于后续的日志过滤和信息构造。
            String methodName = "";
            String className = "";

            // 获取调用者数据，即调用日志方法的代码位置信息。
            StackTraceElement[] callerData = event.getCallerData();
            // 如果有调用者数据，并且至少有一个调用者栈帧，則提取方法名和类名。
            if (callerData != null && callerData.length > 0) {
                methodName = event.getCallerData()[0].getMethodName();
                className = event.getCallerData()[0].getClassName();
            }

            // 如果当前类名不以groupName开头，则忽略此次日志推送。
            // 这是一种简单的日志过滤机制，用于减少不必要的日志推送。
            if (!className.startsWith(groupId)) {
                return;
            }

            // 构造LogMessage对象，包含系统名、类名、方法名和日志消息内容。
            LogMessage logMessage = new LogMessage(
                    systemName,
                    className,
                    methodName,
                    Arrays.asList(event.getFormattedMessage().split(" ")));

            // 将LogMessage推送到Redis中。
            redisPush.push(logMessage);
        }
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
