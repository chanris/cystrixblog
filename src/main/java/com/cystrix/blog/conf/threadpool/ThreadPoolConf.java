package com.cystrix.blog.conf.threadpool;

import com.cystrix.blog.util.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.util.concurrent.*;

/**
 * @author chenyue7@foxmail.com
 * @date 2025/6/1
 * @description
 */
@Configuration
@Slf4j
public class ThreadPoolConf {

    private final ThreadPoolExecutor ipHandlerThreadPoolExecutor = ThreadPoolUtil.createThreadPool("ipHandler");

    @Bean(name = "ipThreadPoolExecutor")
    public ThreadPoolExecutor ipThreadPoolExecutor() {
        return ipHandlerThreadPoolExecutor;
    }

    @PreDestroy
    public void shutdownThreadPool() {
        log.info("关闭 ipHandlerThreadPoolExecutor");
        ThreadPoolUtil.shutdown(ipHandlerThreadPoolExecutor);
        log.info("ipHandlerThreadPoolExecutor 关闭成功");
    }
}
