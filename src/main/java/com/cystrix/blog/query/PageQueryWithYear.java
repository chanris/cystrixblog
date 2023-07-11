package com.cystrix.blog.query;

/**
 * @author: chenyue7@foxmail.com
 * @date: 11/7/2023
 * @description: 根据文章发表年份筛选文章，分页展示
 */
public class PageQueryWithYear extends PageQuery{
    private Integer year;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
