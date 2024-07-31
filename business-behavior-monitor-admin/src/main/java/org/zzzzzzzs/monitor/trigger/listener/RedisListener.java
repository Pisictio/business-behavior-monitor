package org.zzzzzzzs.monitor.trigger.listener;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.stereotype.Component;
import org.zzzzzzzs.monitor.domain.service.ILogAnalyticalService;
import org.zzzzzzzs.sdk.model.LogMessage;

import javax.annotation.Resource;

/**
 * @author zzs
 * @description 用于接收和处理Redis发布的消息。
 * @create 2024/7/30 13:38
 */
@Slf4j
@Component
public class RedisListener implements MessageListener<LogMessage> {

    @Resource
    private ILogAnalyticalService logAnalyticalService;

    /**
     * 当收到消息时，将消息内容记录到日志。
     *
     * @param charSequence 消息的通道名称。
     * @param logMessage   接收到的LogMessage对象。
     */
    @Override
    public void onMessage(CharSequence charSequence, LogMessage logMessage) {
        try {
            log.info("监控日志信息: {}", JSON.toJSONString(logMessage));
            logAnalyticalService.doAnalytical(logMessage.getSystemName(), logMessage.getClassName(), logMessage.getMethodName(), logMessage.getLogList());
        } catch (Exception e) {
            log.error("监控日志信息，解析失败: {}", JSON.toJSONString(logMessage), e);
        }
    }
}
