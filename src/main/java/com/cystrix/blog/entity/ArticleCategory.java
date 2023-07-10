package com.cystrix.blog.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
@Data
public class ArticleCategory {
    private Integer id;
    private Integer articleId;
    private Integer categoryId;
}
