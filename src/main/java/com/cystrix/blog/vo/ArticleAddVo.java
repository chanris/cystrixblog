package com.cystrix.blog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 26/12/2023
 * @description
 */
@Getter
@Setter
public class ArticleAddVo {
    private Integer id;
    private String title;
    private String content;
    private String digest;
    private Integer wordNum;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private Integer hotRank;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    private Integer isDelete;

    private List<Integer>  tagIdList;
    private Integer categoryId;
}
