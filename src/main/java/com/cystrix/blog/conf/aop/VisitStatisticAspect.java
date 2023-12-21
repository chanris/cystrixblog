package com.cystrix.blog.conf.aop;

import com.cystrix.blog.dao.SiteHistoryDao;
import com.cystrix.blog.entity.SiteHistory;
import com.cystrix.blog.enums.RedisEnum;
import com.cystrix.blog.util.NetUtils;
import com.cystrix.blog.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author chenyue7@foxmail.com
 * @date 21/12/2023
 * @description 访问统计切面
 */
@Component
@Slf4j
@Aspect
public class VisitStatisticAspect {

    @Resource
    private NetUtils netUtils;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private SiteHistoryDao siteHistoryDao;

    // 声明切入点
    @Pointcut("execution(* com.cystrix.blog.controller..*(..))")
    private void inControllerLayer() {}

    // 获得请求信息
    @Before("inControllerLayer()")
    public void doSiteStatistic(JoinPoint jp) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 确保RequestAttributes是ServletRequestAttributes类型
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            String realIp = netUtils.getIpAddress(request);
            String uri = request.getRequestURI();
            // log.info("Request URI: {}, IP: {}", uri, realIp);
            SiteHistory history = new SiteHistory();
            history.setMethod(request.getMethod());
            history.setRealIP(realIp);
            history.setUri(uri);
            siteHistoryDao.insert(history);
            netUtils.setIPInfo(history);
            boolean flag = redisUtils.isExistedKey(RedisEnum.VISITOR_IP_PREFIX_.name() + realIp);

        }
    }
}
