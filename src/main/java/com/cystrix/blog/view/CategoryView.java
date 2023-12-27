package com.cystrix.blog.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 26/12/2023
 * @description
 */
@Getter
@Setter
@ToString
public class CategoryView {
    private Integer id;
    private Integer pid;
    private List<CategoryView> children;
    private Integer level;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    private Integer isDelete;
    // 前端显示字段
    private String value;
    private String label;
}
