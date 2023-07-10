package com.cystrix.blog.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
@Data
public class Article {
    private Integer id;
    private String title;
    private String content;
    private String digest;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private Integer hotCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer isDelete;
}
