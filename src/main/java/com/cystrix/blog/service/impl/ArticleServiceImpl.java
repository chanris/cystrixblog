package com.cystrix.blog.service.impl;

import com.cystrix.blog.dao.*;
import com.cystrix.blog.entity.*;
import com.cystrix.blog.exception.BusinessException;
import com.cystrix.blog.exception.ParameterException;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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

    private final CoverDao coverDao;

    public ArticleServiceImpl(ArticleDao articleDao, ArticleTagDao articleTagDao, ArticleCategoryDao articleCategoryDao,
                              ArticleCommentDao articleCommentDao, TagDao tagDao, CategoryDao categoryDao, CommentDao commentDao, CoverDao coverDao) {
        this.articleDao = articleDao;
        this.articleTagDao = articleTagDao;
        this.articleCategoryDao = articleCategoryDao;
        this.articleCommentDao = articleCommentDao;
        this.tagDao = tagDao;
        this.categoryDao = categoryDao;
        this.commentDao = commentDao;
        this.coverDao = coverDao;
    }

    @Override
    public List<Article> getPagedArticleWithoutContent(BaseVo vo) {
        executePage(vo);
        return articleDao.selectPageWithoutContent();
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

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void updateArticleCoverImg(MultipartFile file, Integer articleId) {
        if (file.isEmpty()) {
            throw new ParameterException("上传的文件不能为空");
        }
        if (file.getContentType() == null) {
            throw new ParameterException("文件类型不能为空");
        }
        switch (file.getContentType()) {
            case "image/jpeg", "image/png" -> {
                try {
                    log.info("文件大小：{}", file.getSize());
                    String fileName = generateFilename() + "." + file.getContentType().split("/")[1];
                    String filePath = "/blog/upload/article/covers/" + fileName;
                    File directory = new File("/blog/upload/article/covers");
                    if (!directory.exists()) {
                        boolean result = directory.mkdirs();
                        if (!result) {
                            throw new BusinessException("创建上传文件夹失败");
                        }
                    }
                    File saveFile = new File(filePath);
                    try (FileOutputStream fos = new FileOutputStream(saveFile)) {
                        fos.write(file.getBytes());
                    }
                    Cover cover = new Cover();
                    cover.setType(file.getContentType());
                    cover.setSize(file.getSize());
                    cover.setName(fileName);
                    cover.setUrl(saveFile.getAbsolutePath());
                    coverDao.add(cover);
                    Article article = new Article();
                    article.setId(articleId);
                    article.setCoverId(cover.getId());
                    articleDao.update(article);
                } catch (Exception e) {
                    throw new BusinessException(e.getMessage());
                }
            }
            default -> throw new ParameterException("文件类型不支持");
        }
    }

    @Override
    public void addLikeCount(Integer articleId) {
        articleDao.addLikeCount(articleId);
    }

    private String generateFilename() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String timestamp = dateFormat.format(new Date());
        int length = 8;
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // 随机选择是大写还是小写字母
            boolean isUpperCase = random.nextBoolean();
            char randomChar = generateRandomLetter(random, isUpperCase);
            randomString.append(randomChar);
        }
        return timestamp + "_" + randomString.toString();
    }

    private char generateRandomLetter(Random random, boolean isUpperCase) {
        int offset = isUpperCase ? 'A' : 'a';
        return (char) (offset + random.nextInt(26));
    }
}
