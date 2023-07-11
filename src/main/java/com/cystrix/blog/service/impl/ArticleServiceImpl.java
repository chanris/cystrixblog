package com.cystrix.blog.service.impl;

import com.cystrix.blog.dao.ArticleDao;
import com.cystrix.blog.entity.Article;
import com.cystrix.blog.service.ArticleService;
import org.springframework.stereotype.Service;

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
    public List<Article> getArticleByPage(Integer pageNum, Integer pageSize) {
        List<Article> articles = articleDao.queryArticleByPage(pageSize, (pageNum - 1) * pageSize);
        return articles;
    }
}
