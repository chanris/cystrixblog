package com.cystrix.blog.service;

import com.cystrix.blog.entity.ArticleCategory;
import com.cystrix.blog.entity.Category;
import com.cystrix.blog.query.PageQuery;
import com.cystrix.blog.view.CategoryView;

import java.util.List;

/**
 * @author: chenyue7@foxmail.com
 * @date: 13/7/2023
 * @description:
 */
public interface CategoryService {

    Integer addCategory(Category category);

    void modifyCategory(Category category);

    void modifyArticleCategory(ArticleCategory articleCategory);

    void deleteById(Integer id);

    List<Category> getPageCategory(PageQuery query);

    List<Category> getTagListByArticleId(Integer articleId);

    CategoryView categoryTree(Integer categoryId);

    Category getCategoryByArticleId(Integer articleId);
}
