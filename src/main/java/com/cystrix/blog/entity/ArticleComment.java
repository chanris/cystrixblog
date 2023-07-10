package com.cystrix.blog.entity;

import lombok.Data;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
@Data
public class ArticleComment {
    private Integer id;
    private Integer articleId;
    private Integer commentId;
}
