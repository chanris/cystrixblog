package com.cystrix.blog.entity;

import com.cystrix.blog.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author chenyue7@foxmail.com
 * @date 5/1/2024
 * @description
 */
@Getter
@Setter
public class ArticleImg extends BaseEntity {
    private Integer id;
    // 文章id
    private Integer articleId;
    // 文件绝对路径
    private String uri;
    // 文件名称
    private String name;
    // 文件类型
    private String type;
    // 文件大小
    private Long size;
}
