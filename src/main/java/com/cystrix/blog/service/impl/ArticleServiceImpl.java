package com.cystrix.blog.service.impl;

import com.cystrix.blog.dao.ArticleDao;
import com.cystrix.blog.entity.Article;
import com.cystrix.blog.service.ArticleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: chenyue7@foxmail.com
 * @date: 11/7/2023
 * @description:
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleDao articleDao;

    public ArticleServiceImpl(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    @Override
    public List<Article> getPagedArticleWithoutContent(Integer pageNum, Integer pageSize) {
        return articleDao.selectPageWithoutContent(pageSize, (pageNum - 1) * pageSize);
    }

    @Override
    public List<Article> getPagedArticleByYearWithoutContent(Integer pageNum, Integer pageSize, Integer year) {
        return articleDao.selectPageByYearWithoutContent(pageSize, (pageNum - 1) * pageSize, year);
    }

    @Override
    public List<Article> listArticleOrderByHotRank() {
        return articleDao.selectArticleListByHotRank(10);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Article getDetailArticle(Integer id) {
        Article article = articleDao.selectArticleById(id);
        Article addViewCount = new Article();
        Integer viewCount = article.getViewCount();
        addViewCount.setId(article.getId());
        addViewCount.setViewCount(viewCount + 1);
        addViewCount.setUpdateTime(LocalDateTime.now());
        articleDao.update(addViewCount);
        return article;
    }

    @Override
    public Article getArticleWithoutContent(Integer id) {
        return articleDao.selectArticleByIdWithoutContent(id);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void addArticle(Article article) {
        article.setHotRank(0);
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        article.setCommentCount(0);
        article.setLikeCount(0);
        article.setViewCount(0);
        articleDao.insert(article);
    }

    @Override
    public void modifyArticle(Article article) {
        articleDao.update(article);
    }

    @Override
    public void removeArticle(Integer id) {
        articleDao.deleteById(id);
    }
}
