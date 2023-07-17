package com.cystrix.blog.service;

import com.cystrix.blog.entity.Category;
import com.cystrix.blog.entity.Tag;
import com.cystrix.blog.query.PageQuery;

import java.util.List;

/**
 * @author: chenyue7@foxmail.com
 * @date: 13/7/2023
 * @description:
 */
public interface CategoryService {

    void addCategory(Category category);

    void modifyCategory(Category category);

    void deleteById(Integer id);

    List<Category> getPageTag(PageQuery query);

    List<Category> getTagListByArticleId(Integer articleId);

}
