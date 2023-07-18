package com.cystrix.blog.dao;

import com.cystrix.blog.entity.ArticleComment;
import org.springframework.stereotype.Repository;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
@Repository
public interface ArticleCommentDao {

    void insert(ArticleComment record);

    void deleteById(Integer id);

    ArticleComment selectOneByArticleId(Integer articleId);
}
