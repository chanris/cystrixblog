package com.cystrix.blog.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenyue7@foxmail.com
 * @date 2025/6/1
 * @description
 */
@Getter
@Setter
public class ArticleComment {
    private Integer id;
    private Integer articleId;
    private Integer commentId;
}
