package com.cystrix.blog.entity;

import com.cystrix.blog.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description: 文章分类
 */
@Getter
@Setter
public class Category extends BaseEntity {
    private Integer id;
    private Integer pid;
    private String name;

    // non-database field
    private Integer level;
    // non-db fields
    private Integer articleNum;
}
