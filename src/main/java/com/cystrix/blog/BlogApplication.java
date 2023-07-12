package com.cystrix.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @author: chenyue7@foxmail.com
 * @date: 6/7/2023
 * @description:
 */


@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "com.cystrix.blog.dao")
public class BlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class);
    }
}
