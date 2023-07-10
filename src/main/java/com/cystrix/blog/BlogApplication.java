package com.cystrix.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * @author: chenyue7@foxmail.com
 * @date: 6/7/2023
 * @description:
 */


@SpringBootApplication
@MapperScan(basePackages = "com.cystrix.blog.entity")
public class BlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class);
    }
}
