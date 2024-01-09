package com.cystrix.blog.entity;

import com.cystrix.blog.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
@Getter
@Setter
public class ArticleTag extends BaseEntity {
    private Integer id;
    private Integer articleId;
    private Integer tagId;

    public ArticleTag(Integer articleId, Integer tagId) {
        this.articleId = articleId;
        this.tagId = tagId;
    }
}
