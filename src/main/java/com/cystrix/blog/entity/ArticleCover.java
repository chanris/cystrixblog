package com.cystrix.blog.entity;

import com.cystrix.blog.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author chenyue7@foxmail.com
 * @date 29/12/2023
 * @description
 */
@Getter
@Setter
public class ArticleCover extends BaseEntity {
    private Integer id;
    private Integer articleId;
    private String url;
    private String name;
    private String type;
    private Long size;
}
