package org.zzzzzzzs.monitor.trigger.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.zzzzzzzs.monitor.domain.model.entity.MonitorDataMapEntity;
import org.zzzzzzzs.monitor.domain.model.vo.MonitorTreeConfigVO;
import org.zzzzzzzs.monitor.domain.service.ILogAnalyticalService;
import org.zzzzzzzs.monitor.trigger.http.dto.MonitorDataMapDTO;
import org.zzzzzzzs.monitor.trigger.http.dto.MonitorFlowDataDTO;
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
    public Response<List<MonitorDataMapDTO>> queryMonitorDataMap() {

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
            log.error("查询监控系统数据失败", e);
            return Response.<List<MonitorDataMapDTO>>builder()
                    .code(ResponseEnum.UN_ERROR.getInfo())
                    .info(ResponseEnum.UN_ERROR.getInfo())
                    .build();
        }
    }

    @GetMapping("query_monitor_flow_map")
    public Response<MonitorFlowDataDTO> queryMonitorFlowMap(@RequestParam String monitorId) {
        try {
            log.info("查询监控系统流程数据 monitorId: {}", monitorId);
            MonitorTreeConfigVO monitorTreeConfigVO = logAnalyticalService.queryMonitorFlowMap(monitorId);
            List<MonitorTreeConfigVO.Node> nodeList = monitorTreeConfigVO.getNodeList();
            List<MonitorTreeConfigVO.Link> linkList = monitorTreeConfigVO.getLinkList();

            // 节点数据转换
            List<MonitorFlowDataDTO.NodeData> nodeDataArray = new ArrayList<>(nodeList.size());
            for (MonitorTreeConfigVO.Node node : nodeList) {
                MonitorFlowDataDTO.NodeData nodeData = new MonitorFlowDataDTO.NodeData(
                        node.getMonitorNodeId(),
                        node.getMonitorNodeId(),
                        node.getMonitorNodeName(),
                        node.getMonitorNodeValue(),
                        node.getLoc(),
                        node.getColor());
                nodeDataArray.add(nodeData);
            }

            // 边数据转换
            List<MonitorFlowDataDTO.NodeLink> nodeLinkArray = new ArrayList<>(linkList.size());
            for (MonitorTreeConfigVO.Link link : linkList) {
                // 判断是否有流量值
                String linkValue = link.getLinkValue();
                MonitorFlowDataDTO.NodeLink nodeLink = "0".equals(linkValue)
                        ? new MonitorFlowDataDTO.NodeLink(link.getFromMonitorNodeId(), link.getToMonitorNodeId())
                        : new MonitorFlowDataDTO.NodeLink(link.getNodeLinkKey(), link.getFromMonitorNodeId(), link.getToMonitorNodeId(), link.getLinkValue());
                nodeLinkArray.add(nodeLink);
            }

            MonitorFlowDataDTO monitorFlowDataDTO = MonitorFlowDataDTO.builder()
                    .nodeDataArray(nodeDataArray)
                    .nodeLinkArray(nodeLinkArray)
                    .build();

            log.info("查询监控系统流程数据成功");
            return Response.<MonitorFlowDataDTO>builder()
                    .code(ResponseEnum.SUCCESS.getCode())
                    .info(ResponseEnum.SUCCESS.getInfo())
                    .data(monitorFlowDataDTO)
                    .build();
        } catch (Exception e) {
            log.error("查询监控系统流程数据失败", e);
            return Response.<MonitorFlowDataDTO>builder()
                    .code(ResponseEnum.UN_ERROR.getInfo())
                    .info(ResponseEnum.UN_ERROR.getInfo())
                    .build();
        }
    }
}
