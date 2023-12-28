package com.cystrix.blog.vo;

import com.cystrix.blog.enums.CodeEnum;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
public class Response {
    private Integer code;
    private String msg;
    private Object result;
    private String refreshToken;

    public Response(Integer code, String msg, Object result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public static Response ok() {
        return  Response.builder().code(CodeEnum.OK.code).msg(CodeEnum.OK.msg).build();
    }
    public static Response ok(Object data) {
        return  Response.builder().code(CodeEnum.OK.code).msg(CodeEnum.OK.msg).result(data).build();
    }

    public static Response failed(CodeEnum codeEnum) {
        return Response.builder().code(codeEnum.code).msg(codeEnum.msg).build();
    }

    public static Response failed(CodeEnum codeEnum, String msg) {
        return Response.builder().code(codeEnum.code).msg(msg).build();
    }

    public static Response failed(CodeEnum codeEnum, String msg, Object data) {
        return Response.builder().code(codeEnum.code).msg(msg).result(data).build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer code;
        private String msg;
        private Object result;

        public Builder code(Integer code) {
            this.code = code;
            return this;
        }

        public Builder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder result(Object result) {
            this.result = result;
            return this;
        }

        public Response build() {
            return new Response(code, msg, result);
        }
    }
}
