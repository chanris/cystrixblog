package com.cystrix.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author: chenyue7@foxmail.com
 * @date: 6/7/2023
 * @description:
 */


@EnableScheduling  // 启用 spring task
@SpringBootApplication
@EnableTransactionManagement
@EnableDiscoveryClient // nacos 服务注册与发现
@MapperScan(basePackages = "com.cystrix.blog.dao")
public class BlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class);
    }
}

