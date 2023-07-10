package com.cystrix.blog.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description: 文章分类
 */
@Data
public class Category {
    private Integer id;
    private String name;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer isDelete;
}
