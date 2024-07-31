package org.zzzzzzzs.sdk.push.impl;

import com.alibaba.fastjson.JSON;
import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zzzzzzzs.sdk.model.LogMessage;
import org.zzzzzzzs.sdk.push.IPush;


/**
 * @author zzs
 * @description Redis发布订阅模式进行消息推送
 * @create 2024/7/28 14:14
 */
public class RedisPush implements IPush {

    /**
     * 日志记录器，用于记录操作日志。
     */
    private final Logger logger = LoggerFactory.getLogger(RedisPush.class);

    /**
     * Redisson客户端实例，用于与Redis进行通信。
     */
    private RedissonClient redissonClient;

    /**
     * 使用给定的主机和端口打开到Redis的连接，并订阅指定的主题。
     *
     * @param host Redis服务器的主机地址。
     * @param port Redis服务器的端口号。
     */
    @Override
    public synchronized void open(String host, int port) {

        if (null != redissonClient && !redissonClient.isShutdown()) {
            return;
        }

        // 配置Redisson客户端，设置连接参数和编码器
        Config config = new Config();
        config.setCodec(JsonJacksonCodec.INSTANCE);
        config.useSingleServer()
                .setAddress("redis://" + host + ":" + port)
                .setConnectionPoolSize(64)
                .setConnectionMinimumIdleSize(10)
                .setIdleConnectionTimeout(1000)
                .setConnectTimeout(1000)
                .setRetryAttempts(3)
                .setRetryInterval(1000)
                .setPingConnectionInterval(0)
                .setKeepAlive(true);

        // 创建Redisson客户端实例
        this.redissonClient = Redisson.create(config);
    }

    /**
     * 向名为"business-behavior-monitor-sdk-topic"的主题发布LogMessage消息。
     *
     * @param logMessage 要发布的LogMessage对象。
     */
    @Override
    public void push(LogMessage logMessage) {
        try {
            // 获取主题对象并发布消息
            RTopic topic = redissonClient.getTopic("business-behavior-monitor-sdk-topic");
            topic.publish(logMessage);
        } catch (Exception e) {
            // 记录推送失败的日志
            logger.error("警告: 业务行为监控组件，推送日志消息失败", e);
        }
    }
}

