package com.cystrix.blog.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author chenyue7@foxmail.com
 * @date 9/1/2024
 * @description
 */
@Getter
@Setter
public class ArticleView {
    private Integer id;
    private String title;
    private String content;
    private String digest;
    private Integer wordNum;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime updateTime;
    private String categoryName;
    private Integer categoryId;
    private String coverImg;
}
