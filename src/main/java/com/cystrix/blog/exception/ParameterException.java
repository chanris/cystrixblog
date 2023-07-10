package com.cystrix.blog.exception;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
public class ParameterException extends RuntimeException{
    public ParameterException() {
        super("请求参数错误");
    }

    public ParameterException(String msg) {
        super(msg);
    }
}
