package com.cystrix.blog.conf.aop;

import com.cystrix.blog.conf.task.SiteTaskService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author chenyue7@foxmail.com
 * @date 4/1/2024
 * @description
 */
@ConditionalOnProperty(name = "access.record.enable")
@Component
@Aspect
public class UpdateSiteInfoAspect {

    @Resource
    private  SiteTaskService siteTaskService;

    // 声明切入点
    @Pointcut("execution(* com.cystrix.blog.controller.admin..*(..)) " +
            "&& !execution(* com.cystrix.blog.controller.admin..get*(..))" +
            "&& !execution(* com.cystrix.blog.controller.admin..login(..))")
    private void inAdminController() {}

    // 更新网站统计数据
    @After("inAdminController()")
    public void  updateSiteInfo(){
        siteTaskService.updateSiteInfo(LocalDateTime.now());
    }
}
