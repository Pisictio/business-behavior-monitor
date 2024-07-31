package org.zzzzzzzs.monitor.test;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class ApiTest {

    private User user = null;

    @Before
    public void init() {
        user = new User();
        user.setUserId("1");
        user.setName("张三");
    }

    @Test
    public void test() throws InterruptedException {

        log.info("测试日志 {} {} {}", user.getUserId(), user.getName(), JSON.toJSONString(user));

        new CountDownLatch(1).await();
    }

    @Test
    public void test_log_00() {
        log.info("测试日志00 {} {} {}", user.getUserId(), user.getName(), JSON.toJSONString(user));
    }

    @Data
    static class User {
        private String userId;
        private String name;
    }
}
