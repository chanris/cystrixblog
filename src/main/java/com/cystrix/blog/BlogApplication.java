package com.cystrix.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author: chenyue7@foxmail.com
 * @date: 6/7/2023
 * @description:
 */
@MapperScan(basePackages = "com.cystrix.blog.entity")
@SpringBootApplication
public class BlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class);
    }
}
