package com.cystrix.blog.query;

import lombok.Data;

/**
 * @author: chenyue7@foxmail.com
 * @date: 11/7/2023
 * @description:
 */
@Data
public class PageQuery {
    private Integer pageNum;
    private Integer pageSize;
}
