package com.cystrix.blog.conf.aop;

import com.cystrix.blog.dao.SiteHistoryDao;
import com.cystrix.blog.dao.SiteInfoDao;
import com.cystrix.blog.entity.SiteHistory;
import com.cystrix.blog.entity.SiteInfo;
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
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author chenyue7@foxmail.com
 * @date 21/12/2023
 * @description 访问量统计切面
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
    @Resource
    private SiteInfoDao siteInfoDao;
    @Resource
    private ThreadPoolExecutor ipThreadPoolExecutor;

    private static final long VISIT_EXPIRE_TIME = 60 * 15;

    // 声明切入点
    @Pointcut("execution(* com.cystrix.blog.controller..*(..))")
    private void inControllerLayer() {}

    /**
     * 统计网站浏览量
     * @param jp
     */
    @Before("inControllerLayer()")
    public void doSiteStatistic(JoinPoint jp) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 确保RequestAttributes是ServletRequestAttributes类型
        if (requestAttributes instanceof ServletRequestAttributes) {
            final HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

            ipThreadPoolExecutor.execute(()->{
                SiteHistory siteHistory = netUtils.getSiteHistory(request);
                if (siteHistory != null) {
                    siteHistoryDao.insert(siteHistory);
                }
            });

            // 设置浏览量
            String realIp = netUtils.getIpAddress(request);
            String key = RedisEnum.VISITOR_IP_PREFIX_.name() + realIp;
            String value = redisUtils.getValue(key);
            if(value == null) {
                siteInfoDao.addVisitNum();
                redisUtils.setValue(key, key);
                redisUtils.setExpireTime(key, VISIT_EXPIRE_TIME);
            }else {
                // 刷新过期时间
                redisUtils.setExpireTime(key, VISIT_EXPIRE_TIME);
            }
        }
    }
}
