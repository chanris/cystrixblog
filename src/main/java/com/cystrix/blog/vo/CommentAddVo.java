package com.cystrix.blog.vo;

import lombok.Data;

/**
 * @author chenyue7@foxmail.com
 * @date 2025/6/1
 * @description
 */
@Data
public class CommentAddVo {
    private Integer articleId;
    private Integer pid;
    private String content;
}
