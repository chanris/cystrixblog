package com.cystrix.blog.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */

@Getter
@Setter
public class ArticleCategory {
    private Integer id;
    private Integer articleId;
    private Integer categoryId;
}
