package com.cystrix.blog.dao;

import com.cystrix.blog.entity.ArticleCategory;
import org.springframework.stereotype.Repository;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
@Repository
public interface ArticleCategoryDao {

    void insert(ArticleCategory articleCategory);

   void deleteByArticleIdAndCategoryId(ArticleCategory articleCategory);
}
