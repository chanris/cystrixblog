package com.cystrix.blog.conf.exception;

import com.cystrix.blog.enums.CodeEnum;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.vo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 全局异常捕捉
    // 捕捉声明的异常及其子异常
    // 如果显示声明了某子异常就不会在该exceptionHandler处理！！
    // 如果有多个父类exceptionHandler 会选择最近的父类exceptionHandler处理
    @ExceptionHandler(value = {Exception.class})
    public Response defaultExceptionHandle(Exception ex){
        log.warn("===========================捕捉到全局异常===========================");
        log.warn("异常类型: {}", ex.getClass().toGenericString());
        log.warn("异常信息：{}", ex.getMessage());
        log.warn("====================================================================");
        return Response.failed(CodeEnum.INTER_SERVER_ERROR);
    }

    @ExceptionHandler(value = {ParameterException.class, HttpMessageNotReadableException.class})
    public Response parameterExceptionHandle(Exception ex) {
        log.warn("===========================捕捉到请求参数异常=========================");
        log.warn("异常类型: {}", ex.getClass().toGenericString());
        log.warn("异常信息：{}", ex.getMessage());
        log.warn("===================================================================");
        return Response.failed(CodeEnum.BAD_REQUEST_PARAMETER, ex.getMessage());
    }


}
