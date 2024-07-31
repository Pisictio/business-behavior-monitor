package org.zzzzzzzs.monitor.trigger;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zzzzzzzs.monitor.trigger.http.MonitorController;
import org.zzzzzzzs.monitor.trigger.http.dto.MonitorDataMapDTO;
import org.zzzzzzzs.monitor.types.Response;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zzs
 * @description
 * @create 2024/7/31 15:39
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MonitorControllerTest {

    @Resource
    private MonitorController monitorController;

    @Test
    public void testQueryMonitorDataMap() {
        Response<List<MonitorDataMapDTO>> response = monitorController.queryMonitorDataMap();
        log.info("测试查询监控数据: {}", JSON.toJSONString(response));
    }
}
