package com.cystrix.blog.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author chenyue7@foxmail.com
 * @date 28/12/2023
 * @description
 */
@Getter
@Setter
public class Motto {
    private Integer id;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer isDelete;
}
