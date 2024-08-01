package org.zzzzzzzs.monitor.domain.model.vo;

import lombok.*;

import java.util.List;

/**
 * @author zzs
 * @description
 * @create 2024/8/1 10:39
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonitorTreeConfigVO {

    private String monitorId;

    private List<Node> nodeList;

    private List<Link> linkList;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Node {
        // 节点id
        private String monitorNodeId;
        // 节点名称
        private String monitorNodeName;
        // 节点坐标
        private String loc;
        // 节点颜色
        private String color;
        // 节点值
        private String monitorNodeValue;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Link {
        // 边标识
        private String nodeLinkKey;
        // 边起点
        private String fromMonitorNodeId;
        // 边终点
        private String toMonitorNodeId;
        // 链路停留值
        private String linkValue;
    }
}
