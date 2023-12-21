package com.cystrix.blog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author chenyue7@foxmail.com
 * @date 21/12/2023
 * @description
 */
@Getter
@Setter
public class SiteHistory {
    private Long id;
    private String realIP;
    private String uri;
    private String method;
    private String params;
    private String city;
    private String region;
    private String country;
    private String loc;
    private String org;
    private String timezone;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
