package com.cystrix.blog.enums;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description: 定义请求响应码
 */
public enum CodeEnum {
    INTER_SERVER_UNAVAILABLE(503, "服务器繁忙"),
    INTER_SERVER_ERROR(500, "服务器内部错误"),
    BAD_REQUEST_PARAMETER(400, "请求参数错误"),
    USER_UNAUTHORIZED(401, "用户未登录"),
    FORBIDDEN_ACCESS(403, "没有权限，禁止登录"),
    OK(200, "请求成功");

    public final Integer code;
    public final  String msg;

    CodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CodeEnum OK() {
        return CodeEnum.OK;
    }
}
