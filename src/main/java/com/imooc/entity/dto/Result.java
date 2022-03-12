package com.imooc.entity.dto;

import lombok.Data;

/**
 * @author Peter
 */
@Data
public class Result<T> {
    private int code; //请求状态码
    private String msg;
    private T data;
}
