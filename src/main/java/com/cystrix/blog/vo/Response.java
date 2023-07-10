package com.cystrix.blog.vo;

import com.cystrix.blog.enums.CodeEnum;
import lombok.Data;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
public class Response {
    private Integer code;
    private String msg;
    private Object data;


    public Response(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Response ok() {
        return  Response.builder().code(CodeEnum.OK.code).msg(CodeEnum.OK.msg).build();
    }
    public static Response ok(Object data) {
        return  Response.builder().code(CodeEnum.OK.code).msg(CodeEnum.OK.msg).data(data).build();
    }

    public static Response failed(CodeEnum codeEnum) {
        return Response.builder().code(codeEnum.code).msg(codeEnum.msg).build();
    }

    public static Response failed(CodeEnum codeEnum, String msg) {
        return Response.builder().code(codeEnum.code).msg(msg).build();
    }

    public static Response failed(CodeEnum codeEnum, String msg, Object data) {
        return Response.builder().code(codeEnum.code).msg(msg).data(data).build();
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private Integer code;
        private String msg;
        private Object data;

        public Builder code(Integer code) {
            this.code = code;
            return this;
        }

        public Builder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder data(Object data){
            this.data = data;
            return this;
        }

        public Response build(){
            return new Response(code, msg, data);
        }
    }
}
