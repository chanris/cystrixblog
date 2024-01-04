package com.cystrix.blog.conf.aop;

import com.cystrix.blog.exception.BusinessException;
import com.cystrix.blog.util.JwtUtils;
import com.cystrix.blog.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author chenyue7@foxmail.com
 * @date 4/1/2024
 * @description
 */
@Component
@Aspect
@Slf4j
public class RefreshTokenAspect {

    @Resource
    private JwtUtils jwtUtils;

    // 声明切入点
    @Pointcut("execution(* com.cystrix.blog.controller.admin..*(..)) " +
            "&& !execution(* com.cystrix.blog.controller.admin.AdminUserInfoController.login(..))")
    private void inControllerLayer() {}

    @Around("inControllerLayer()")
    public Object refreshToken(ProceedingJoinPoint joinPoint) {
        try {
            Object response = joinPoint.proceed();
            if (response instanceof Response) {
                Response data = (Response) response;
                Subject subject = SecurityUtils.getSubject();
                if(subject.isAuthenticated()) {
                    Integer userId = (Integer)subject.getPrincipal();
                    data.setRefreshToken(jwtUtils.refreshToken(userId));
                }
            }
            return response;
        }catch (Throwable e) {
            throw new BusinessException("刷新token错误: " + e.getMessage());
        }
    }
}
