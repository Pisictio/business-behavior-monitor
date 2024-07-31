package org.zzzzzzzs.monitor;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zzzzzzzs.monitor.trigger.listener.RedisListener;
import org.zzzzzzzs.sdk.model.LogMessage;

/**
 * @author zzs
 * @description 启动类
 * @create 2024/7/28 10:32
 */

@SpringBootApplication
@Configuration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    /**
     * Redis配置类，用于初始化Redis客户端。
     */
    @EnableConfigurationProperties(RedisClientConfigProperties.class)
    public static class RedisConfig {

        /**
         * 创建Redisson客户端。
         *
         * @param applicationContext 应用上下文，用于获取配置属性。
         * @param properties         Redis客户端的配置属性。
         * @return Redisson客户端实例。
         */
        @Bean("redissonClient")
        public RedissonClient redisClient(ConfigurableApplicationContext applicationContext, RedisClientConfigProperties properties) {
            Config config = new Config();
            // 使用JSON编解码器
            config.setCodec(JsonJacksonCodec.INSTANCE);
            // 配置单个Redis服务器
            config.useSingleServer()
                    // 设置Redis服务器地址
                    .setAddress("redis://" + properties.getHost() + ":" + properties.getPort())
                    // 配置连接池大小
                    .setConnectionPoolSize(properties.getPoolSize())
                    // 配置最小空闲连接数
                    .setConnectionMinimumIdleSize(properties.getMinIdleSize())
                    // 设置空闲连接超时时间
                    .setIdleConnectionTimeout(properties.getIdleTimeout())
                    // 设置连接超时时间
                    .setConnectTimeout(properties.getConnectTimeout())
                    // 设置重试次数
                    .setRetryAttempts(properties.getRetryAttempts())
                    // 设置重试间隔
                    .setRetryInterval(properties.getRetryInterval())
                    // 设置ping连接间隔
                    .setPingConnectionInterval(properties.getPingInterval())
                    // 设置是否保持连接 alive
                    .setKeepAlive(properties.keepAlive);
            return Redisson.create(config);
        }
    }

    @Bean("businessBehaviorMonitorTopic")
    public RTopic businessBehaviorMonitorTopic(RedissonClient redissonClient, RedisListener redisListener) {
        RTopic topic = redissonClient.getTopic("business-behavior-monitor-sdk-topic");
        topic.addListener(LogMessage.class, redisListener);
        return topic;
    }

    /**
     * Redis客户端配置属性类。
     * <p>
     * 该类用于绑定配置文件中redis.sdk.config相关的属性。
     */
    @Data
    @ConfigurationProperties(prefix = "redis.sdk.config", ignoreInvalidFields = true)
    public static class RedisClientConfigProperties {

        /**
         * Redis服务器主机地址。
         */
        private String host;
        /**
         * Redis服务器端口。
         */
        private Integer port;
        /**
         * 连接池大小，默认为64。
         */
        private Integer poolSize = 64;
        /**
         * 最小空闲连接数，默认为10。
         */
        private Integer minIdleSize = 10;
        /**
         * 空闲连接超时时间，默认为10000毫秒。
         */
        private Integer idleTimeout = 10000;
        /**
         * 连接超时时间，默认为10000毫秒。
         */
        private Integer connectTimeout = 10000;
        /**
         * 重试次数，默认为3次。
         */
        private Integer retryAttempts = 3;
        /**
         * 重试间隔，默认为1000毫秒。
         */
        private Integer retryInterval = 1000;
        /**
         * ping连接间隔，默认为0，表示不执行ping操作。
         */
        private Integer pingInterval = 0;
        /**
         * 是否保持连接alive，默认为true。
         */
        private Boolean keepAlive = true;
    }

}
