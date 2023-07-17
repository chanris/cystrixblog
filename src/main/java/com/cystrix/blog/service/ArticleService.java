package com.cystrix.blog.service;

import com.cystrix.blog.entity.Article;
import com.cystrix.blog.entity.ArticleCategory;
import com.cystrix.blog.entity.ArticleTag;

import java.util.List;

/**
 * @author: chenyue7@foxmail.com
 * @date: 11/7/2023
 * @description:
 */
public interface ArticleService {
    List<Article> getPagedArticleWithoutContent(Integer pageNum, Integer pageSize);

    List<Article> getPagedArticleByYearWithoutContent(Integer pageNum, Integer pageSize, Integer year);

    List<Article> listArticleOrderByHotRank();

    Article getDetailArticle(Integer id);

    Article getArticleWithoutContent(Integer id);

    void addArticle(Article article);

    void modifyArticle(Article article);

    void removeArticle(Integer id);

    void addTagInfo(ArticleTag articleTag);
    void addCategoryInfo(ArticleCategory articleCategory);

    List<Article> getArticleDigestInfoByTagId(Integer tagId);
}
