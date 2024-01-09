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
public class Tag extends BaseEntity {
    private Integer id;
    private String name;
    // rgb颜色值
    private String color;
}
