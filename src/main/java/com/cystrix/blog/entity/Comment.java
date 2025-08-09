package com.cystrix.blog.entity;

import com.cystrix.blog.entity.base.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author chenyue7@foxmail.com
 * @date 2025/5/29
 * @description
 */
@Setter
@Getter
public class Comment extends BaseEntity {
    private Integer id;
    private Integer pid;
    private String content;
    private String userIp;
    private String local;
}
