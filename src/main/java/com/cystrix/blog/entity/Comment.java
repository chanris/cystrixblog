package com.cystrix.blog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: :chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description: 文章评论
 */
@Data
public class Comment {
    private Integer id;
    private Integer pid;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    private String userIp;
    private Integer isDelete;
}
