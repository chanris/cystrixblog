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

    /*默认根据创建事件排序分页*/
    List<Article> selectPageWithoutContent(@Param("pageSize") int pageSize, @Param("offset") int offset);

    List<Article> selectPageByYearWithoutContent(@Param("pageSize") int pageSize, @Param("offset") int offset, @Param("year") int year);

    List<Article> selectArticleListByHotRank(int num);

    Article selectArticleById(Integer id);

    Article selectArticleByIdWithoutContent(Integer id);

    void insert(Article article);

    void update(Article article);

    void deleteById(Integer id);
}
