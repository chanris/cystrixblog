package com.cystrix.blog.service;

import com.cystrix.blog.entity.Article;

import java.util.List;

/**
 * @author: chenyue7@foxmail.com
 * @date: 11/7/2023
 * @description:
 */
public interface ArticleService {
    List<Article> getPagedArticleWithoutContent(Integer pageNum, Integer pageSize);

    List<Article> getPagedArticleByYearWithoutContent(Integer pageNum, Integer pageSize, Integer year);

    Article getArticle(Integer id);

    Article getArticleWithoutContent(Integer id);
}
