package com.cystrix.blog.service;

import com.cystrix.blog.entity.Article;

import java.util.List;

/**
 * @author: chenyue7@foxmail.com
 * @date: 11/7/2023
 * @description:
 */
public interface ArticleService {
    List<Article> getPagedArticle(Integer pageNum, Integer pageSize);

    List<Article> getPagedArticleByYear(Integer pageNum, Integer pageSize, Integer year);
}
