package com.cystrix.blog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author chenyue7@foxmail.com
 * @date 20/12/2023
 * @description
 */
@Getter
@Setter
public class SiteInfo {
    private Integer id;
    private Integer articleNum;
    private Integer runDays;
    private Integer wordsNum;
    private Integer visitorsNum;
    private Integer visitNum;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime latestUpdateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
