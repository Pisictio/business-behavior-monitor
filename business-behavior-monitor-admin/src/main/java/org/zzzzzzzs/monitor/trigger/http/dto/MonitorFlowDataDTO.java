package org.zzzzzzzs.monitor.trigger.http.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

/**
 * @author zzs
 * @description
 * @create 2024/7/31 16:45
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MonitorFlowDataDTO {

    private String monitorId;
    @JsonProperty("class")
    private String clazz = "";
    private ModelData modelData = new ModelData();

    private List<NodeData> nodeDataArray;

    private List<NodeLink> nodeLinkArray;

    @Data
    public static class ModelData {
        private final String position = "-5 -5";

    }

    @Data
    public static class NodeData {
        // 节点标识
        private String key;
        // 节点内容
        private String text;
        // 节点坐标
        private String loc;
        // 节点颜色
        private String fill;
        // 节点备注
        private String remark;

        public NodeData(String key, String text01, String text02, String text03, String loc, String fill) {
            this.key = key;
            this.text = "编号" + text01 + "\r\n" + "名称" + text02 + "\r\n" + "次数" + text03;
            this.loc = loc;
            this.fill = fill;
        }

        @Getter
        @AllArgsConstructor
        @NoArgsConstructor
        public enum Color {
            green("#2ECC40", "绿色"),
            red("#DC143C", "红色"),
            yellow("#FFDC00", "黄色");

            private String code;
            private String info;
        }
    }

    @Data
    public static class NodeLink {
        // 边标识
        private String key;
        // 边起点
        private String from;
        // 边终点
        private String to;
        // 边信息
        private String text;
        // 边备注
        private String remark;
        // 边情况
        private String condition;

        public NodeLink(String from, String to) {
            this.from = from;
            this.to = to;
        }

        public NodeLink(String key, String from, String to, String text) {
            this.key = key;
            this.from = from;
            this.to = to;
            this.text = text;
        }
    }

}
