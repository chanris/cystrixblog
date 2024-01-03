package com.cystrix.blog.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 2/1/2024
 * @description
 */
@Getter
@Setter
public class ArticleCategoryView {
    private Integer id;
    private Integer pid;

    private String name;
    private Integer articleNum;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    private Integer isDelete;
}
