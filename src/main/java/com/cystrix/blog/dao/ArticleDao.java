package com.cystrix.blog.dao;

import com.cystrix.blog.entity.Article;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
@Repository
public interface ArticleDao {
    void insert(Article article);

    List<Article> selectPage(@Param("pageSize") int pageSize, @Param("offset") int offset);

    List<Article> selectPageByYear(@Param("pageSize") int pageSize, @Param("offset") int offset, @Param("year") int year);

    Article selectArticleById(Integer id);
}
