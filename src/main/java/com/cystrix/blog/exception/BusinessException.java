package com.cystrix.blog.exception;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
public class BusinessException extends RuntimeException{
    public BusinessException(){
        super();
    }
    public BusinessException(String msg) {
        super(msg);
    }
}
