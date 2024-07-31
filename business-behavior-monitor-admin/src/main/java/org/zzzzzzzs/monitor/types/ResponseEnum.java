package org.zzzzzzzs.monitor.types;

import lombok.*;

/**
 * @author zzs
 * @description
 * @create 2024/7/31 15:33
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ResponseEnum {

    SUCCESS("0000", "成功"),
    UN_ERROR("0001", "未知失败");

    private String code;
    private String info;
}
