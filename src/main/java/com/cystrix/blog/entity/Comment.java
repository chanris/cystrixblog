package com.cystrix.blog.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description: 文章评论
 */
@Data
public class Comment {
    private Integer id;

    private Integer pid;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String userIp;
    private Integer isDelete;
}
