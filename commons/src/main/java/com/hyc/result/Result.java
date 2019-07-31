package com.hyc.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: yche
 * @Date: 2019/07/29
 * @Description 接口返回对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 9162445586881411035L;
    /**
     * 返回码
     */
    private int code = ResultCode.OK.getCode();

    /**
     * 返回描述
     */
    private String msg;

    /**
     * 返回数据
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    private static <T> Result build(ResultCode resultCode, T data) {
        //noinspection unchecked
        return new Result(resultCode.getCode(), resultCode.getMsg(), data);
    }

    public static Result ok() {
        return ok(null);
    }

    public static <T> Result ok(T data) {
        return build(ResultCode.OK, data);
    }

    public static Result fail(ResultCode resultCode) {
        return build(resultCode, null);
    }

    public static Result fail(String msg) {
        //noinspection unchecked
        return new Result(ResultCode.FAIL.getCode(), msg, null);
    }
}

