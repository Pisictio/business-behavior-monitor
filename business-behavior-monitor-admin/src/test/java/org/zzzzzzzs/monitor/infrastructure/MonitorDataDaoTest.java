package org.zzzzzzzs.monitor.infrastructure;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zzzzzzzs.monitor.infrastructure.dao.IMonitorDataDao;
import org.zzzzzzzs.monitor.infrastructure.po.MonitorData;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zzs
 * @description 测试MonitorDataDao
 * @create 2024/7/29 23:13
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MonitorDataDaoTest {

    @Resource
    private IMonitorDataDao monitorDataDao;

    @Test
    public void testQueryMonitorDataList() {
        MonitorData monitorData = new MonitorData();
        List<MonitorData> data = monitorDataDao.queryMonitorDataList(monitorData);
        log.info("data: {}", JSON.toJSONString(data));
    }
}
