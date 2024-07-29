package com.zzzzzzzs.monitor.test;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
/**
 * @author zzs
 * @description
 * @create 2024/7/28 11:22
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@SpringBootConfiguration
public class ApiTest {

    private User user = null;

    @Before
    public void init() {
        user = new User();
        user.setId("1");
        user.setName("张三");
    }

    @Test
    public void test() throws InterruptedException {

        log.info("测试日志 {} {} {}", user.getId(), user.getName(), JSON.toJSONString(user));

        new CountDownLatch(1).await();
    }

    @Data
    static class User {
        private String Id;
        private String name;
    }
}
