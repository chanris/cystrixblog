package com.cystrix.blog.vo;

/**
 * @author chenyue7@foxmail.com
 * @date 20/12/2023
 * @description
 */
public class BaseVo {
    private Integer pageNum = 1;
    private Integer pageSize = 10;

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
