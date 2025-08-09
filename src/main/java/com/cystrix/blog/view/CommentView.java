package com.cystrix.blog.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 2025/5/29
 * @description
 */
@Data
public class CommentView {

    private Integer id;
    // private Integer articleId;
    private String content;
    private String userIp;
    private String local;
    private List<CommentView> children;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
