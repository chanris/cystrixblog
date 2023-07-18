package com.cystrix.blog.exception;

/**
 * @author: chenyue7@foxmail.com
 * @date: 18/7/2023
 * @description:
 */
public class EmailOpsException extends RuntimeException {
    public EmailOpsException() {
        super("邮箱操作异常");
    }

    public EmailOpsException(String msg) {
        super(msg);
    }
}
