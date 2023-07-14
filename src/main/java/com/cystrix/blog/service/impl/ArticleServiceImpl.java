package com.cystrix.blog.service.impl;

import com.cystrix.blog.dao.ArticleDao;
import com.cystrix.blog.dao.ArticleTagDao;
import com.cystrix.blog.dao.TagDao;
import com.cystrix.blog.entity.Article;
import com.cystrix.blog.entity.ArticleTag;
import com.cystrix.blog.entity.Tag;
import com.cystrix.blog.service.ArticleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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

    private final ArticleTagDao articleTagDao;

    private final TagDao tagDao;

    public ArticleServiceImpl(ArticleDao articleDao, ArticleTagDao articleTagDao, TagDao tagDao) {
        this.articleDao = articleDao;
        this.articleTagDao = articleTagDao;
        this.tagDao = tagDao;
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

    /**
     * 为文章添加标签信息
     * @param articleTag
     */
    @Override
    public void addTagInfo(ArticleTag articleTag) {
        Integer articleId = articleTag.getArticleId();
        Integer tagId = articleTag.getTagId();
        Article article = articleDao.selectArticleById(articleId);
        Tag tag = tagDao.selectTagById(tagId);
        Assert.notNull(article, "找不到文章 articleId:" + articleId);
        Assert.notNull(tag, "找不到标签 tagId:" + tagId);
        articleTagDao.insert(articleTag);
    }
}
