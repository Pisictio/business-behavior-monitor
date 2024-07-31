package org.zzzzzzzs.monitor.types;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zzs
 * @description 响应结果
 * @create 2024/7/30 13:20
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 43342342342342L;

    private String code;
    private String info;
    private T data;
}
