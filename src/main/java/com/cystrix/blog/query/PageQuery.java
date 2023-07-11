package com.cystrix.blog.query;


/**
 * @author: chenyue7@foxmail.com
 * @date: 11/7/2023
 * @description:
 */
public class PageQuery {
    private Integer pageNum;
    private Integer pageSize;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
