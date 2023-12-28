package com.cystrix.blog.service.impl;

import com.cystrix.blog.dao.*;
import com.cystrix.blog.entity.*;
import com.cystrix.blog.service.ArticleService;
import com.cystrix.blog.service.BaseService;
import com.cystrix.blog.util.StringUtils;
import com.cystrix.blog.vo.ArticleAddVo;
import com.cystrix.blog.vo.ArticleVo;
import com.cystrix.blog.vo.BaseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: chenyue7@foxmail.com
 * @date: 11/7/2023
 * @description:
 */
@Slf4j
@Service
public class ArticleServiceImpl extends BaseService implements ArticleService {

    private final ArticleDao articleDao;

    private final ArticleTagDao articleTagDao;

    private final ArticleCategoryDao articleCategoryDao;

    private final ArticleCommentDao articleCommentDao;

    private final CategoryDao categoryDao;

    private final TagDao tagDao;

    private final CommentDao commentDao;

    public ArticleServiceImpl(ArticleDao articleDao, ArticleTagDao articleTagDao, ArticleCategoryDao articleCategoryDao,
                              ArticleCommentDao articleCommentDao, TagDao tagDao, CategoryDao categoryDao, CommentDao commentDao) {
        this.articleDao = articleDao;
        this.articleTagDao = articleTagDao;
        this.articleCategoryDao = articleCategoryDao;
        this.articleCommentDao = articleCommentDao;
        this.tagDao = tagDao;
        this.categoryDao = categoryDao;
        this.commentDao = commentDao;
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
//        addViewCount.setUpdateTime(LocalDateTime.now());
        articleDao.update(addViewCount);
        return article;
    }

    @Override
    public Article getArticleWithoutContent(Integer id) {
        return articleDao.selectArticleByIdWithoutContent(id);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void addArticle(ArticleAddVo vo) {
        // 添加文章 设置 摘要 & 字数
        int wordNum = StringUtils.countWords(vo.getContent());
        vo.setWordNum(wordNum);
        Article record = new Article();
        record.setTitle(vo.getTitle());
        record.setDigest(vo.getDigest());
        record.setContent(vo.getContent());
        record.setWordNum(vo.getWordNum());
        articleDao.insert(record);
        // 关联分类
        ArticleCategory ref = new ArticleCategory();
        if(vo.getCategoryId() != null) {
            ref.setArticleId(record.getId());
            ref.setCategoryId(vo.getCategoryId());
        }else {
            // 默认分类
            ref.setArticleId(record.getId());
            ref.setCategoryId(1);
        }
        articleCategoryDao.insert(ref);
        if(vo.getTagIdList() != null && vo.getTagIdList().size() != 0) {
            // 关联标签
            List<ArticleTag> articleTags = new ArrayList<>();
            vo.getTagIdList().forEach(item -> {
                articleTags.add(new ArticleTag(record.getId(), item));
            });
            articleTagDao.batchInsert(articleTags);
        }
    }

    @Override
    public void modifyArticle(Article article) {
        // 文章字数
        if(article.getContent() != null) {
            String content =  article.getContent();
            // String digest  = StringUtils.generateDigest(content);
            int wordNum = StringUtils.countWords(content);
            // article.setDigest(digest);
            article.setWordNum(wordNum);
        }
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

    @Override
    public void addCategoryInfo(ArticleCategory articleCategory) {
        Integer articleId = articleCategory.getArticleId();
        Integer categoryId = articleCategory.getCategoryId();
        Article article = articleDao.selectArticleById(articleId);
        Category category = categoryDao.selectCategoryById(categoryId);
        Assert.notNull(article, "找不到文章 articleId:" + articleId);
        Assert.notNull(category, "找不到分类 categoryId:" + categoryId);
        articleCategoryDao.insert(articleCategory);
    }

    @Override
    public void addCommentInfo(ArticleComment articleComment) {
        Integer articleId = articleComment.getArticleId();
        Integer commentId = articleComment.getCommentId();
        Article article = articleDao.selectArticleById(articleId);
        Comment comment = commentDao.selectCommentById(commentId);
        Assert.notNull(article, "找不到文章 articleId:" + articleId);
        Assert.notNull(comment, "找不到评论 commentId:" + commentId);
        articleCommentDao.insert(articleComment);
    }

    @Override
    public List<Article> getArticleDigestInfoByTagId(Integer tagId) {
        List<Integer> list = articleTagDao.selectArticleIdByTagId(tagId);
        if (list.size() != 0) {
            return articleDao.selectArticleByIdsWithoutContent(list);
        }
        return new ArrayList<>();
    }

    @Override
    public List<Article> getArticleDigestInfoByCategoryId(Integer categoryId) {
        List<Integer> list = articleCategoryDao.selectArticleIdByCategoryId(categoryId);
        if (list.size() != 0) {
            return articleDao.selectArticleByIdsWithoutContent(list);
        }
        return new ArrayList<>();
    }

    @Override
    public List<Article> getArticleInfoWithPage(ArticleVo vo) {
        executePage(vo);
        return articleDao.selectArticleWithPage(vo);
    }
}
