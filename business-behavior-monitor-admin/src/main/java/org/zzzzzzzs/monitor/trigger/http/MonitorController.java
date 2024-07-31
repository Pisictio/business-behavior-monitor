package org.zzzzzzzs.monitor.trigger.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zzzzzzzs.monitor.domain.model.entity.MonitorDataMapEntity;
import org.zzzzzzzs.monitor.domain.service.ILogAnalyticalService;
import org.zzzzzzzs.monitor.trigger.http.dto.MonitorDataMapDTO;
import org.zzzzzzzs.monitor.types.Response;
import org.zzzzzzzs.monitor.types.ResponseEnum;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zzs
 * @description
 * @create 2024/7/31 15:16
 */
@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/monitor")
public class MonitorController {

    @Resource
    private ILogAnalyticalService logAnalyticalService;

    /**
     * 查询监控系统数据
     * <a href="http://localhost:8092/api/v1/monitor/query_monitor_data_map">/api/v1/monitor/query_monitor_data_map</a>
     *
     * @return 监控系统数据
     */
    @GetMapping("query_monitor_data_map")
    public Response<List<MonitorDataMapDTO>> queryMonitorDataMap(){

        try {
            log.info("查询监控系统数据");
            List<MonitorDataMapEntity> monitorDataMapEntities = logAnalyticalService.queryMonitorDataMap();
            List<MonitorDataMapDTO> monitorDataMapDTOS = new ArrayList<>(monitorDataMapEntities.size());
            for (MonitorDataMapEntity monitorDataMapEntity : monitorDataMapEntities) {
                MonitorDataMapDTO monitorDataMapDTO = MonitorDataMapDTO.builder()
                        .monitorId(monitorDataMapEntity.getMonitorId())
                        .monitorName(monitorDataMapEntity.getMonitorName())
                        .build();
                monitorDataMapDTOS.add(monitorDataMapDTO);
            }
            log.info("查询监控系统数据成功");
            return Response.<List<MonitorDataMapDTO>>builder()
                    .code(ResponseEnum.SUCCESS.getCode())
                    .info(ResponseEnum.SUCCESS.getInfo())
                    .data(monitorDataMapDTOS)
                    .build();
        } catch (Exception e) {
            return Response.<List<MonitorDataMapDTO>>builder()
                    .code(ResponseEnum.UN_ERROR.getInfo())
                    .info(ResponseEnum.UN_ERROR.getInfo())
                    .build();
        }}
}
