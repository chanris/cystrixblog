package com.cystrix.blog.service;

import com.cystrix.blog.entity.Article;
import com.cystrix.blog.entity.ArticleCategory;
import com.cystrix.blog.entity.ArticleComment;
import com.cystrix.blog.entity.ArticleTag;
import com.cystrix.blog.vo.ArticleAddVo;
import com.cystrix.blog.vo.ArticleVo;
import com.cystrix.blog.vo.BaseVo;

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

    void addArticle(ArticleAddVo article);

    void modifyArticle(Article article);

    void removeArticle(Integer id);

    void addTagInfo(ArticleTag articleTag);

    void addCategoryInfo(ArticleCategory articleCategory);

    void addCommentInfo(ArticleComment articleComment);

    List<Article> getArticleDigestInfoByTagId(Integer tagId);

    List<Article> getArticleDigestInfoByCategoryId(Integer categoryId);

    List<Article> getArticleInfoWithPage(ArticleVo vo);

}
