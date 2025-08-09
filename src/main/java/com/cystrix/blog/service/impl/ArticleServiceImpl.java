package com.cystrix.blog.service.impl;

import com.cystrix.blog.dao.*;
import com.cystrix.blog.entity.*;
import com.cystrix.blog.exception.BusinessException;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.service.BaseService;
import com.cystrix.blog.util.StringUtils;
import com.cystrix.blog.view.ArticleView;
import com.cystrix.blog.vo.ArticleAddVo;
import com.cystrix.blog.vo.ArticleVo;
import com.cystrix.blog.vo.BaseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
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
public class ArticleServiceImpl extends BaseService {

    private final ArticleDao articleDao;
    private final ArticleTagDao articleTagDao;
    private final ArticleCategoryDao articleCategoryDao;

    private final CategoryDao categoryDao;
    private final TagDao tagDao;

    private final ArticleCoverDao articleCoverDao;
    private final ArticleImgDao articleImgDao;

    @Value("${path.img}")
    private String imgPath;
    @Value("${path.cover}")
    private String coverPath;

    public ArticleServiceImpl(ArticleDao articleDao, ArticleTagDao articleTagDao, ArticleCategoryDao articleCategoryDao,
                             TagDao tagDao, CategoryDao categoryDao,
                              ArticleCoverDao articleCoverDao, ArticleImgDao articleImgDao) {
        this.articleDao = articleDao;
        this.articleTagDao = articleTagDao;
        this.articleCategoryDao = articleCategoryDao;
        this.tagDao = tagDao;
        this.categoryDao = categoryDao;
        this.articleCoverDao = articleCoverDao;
        this.articleImgDao = articleImgDao;
    }

    public List<ArticleView> getPagedArticleWithoutContent(BaseVo vo) {
        executePage(vo);
        return articleDao.selectPageWithoutContent();
    }

    /**
     * 后台获得文章详情
     * @param articleId 文章id
     * @return ArticleView
     */
    public ArticleView editDetailArticle(Integer articleId) {
        return articleDao.selectArticleViewById(articleId);
    }

    /**
     * 前台获得文章详情，更新阅读量
     * @param id
     * @return
     */
    @Transactional(rollbackFor = {Exception.class})
    public ArticleView getDetailArticle(Integer id) {
        ArticleView article = articleDao.selectArticleViewById(id);
        Article addViewCount = new Article();
        Integer viewCount = article.getViewCount();
        addViewCount.setId(article.getId());
        addViewCount.setViewCount(viewCount + 1);
        articleDao.update(addViewCount);
        return article;
    }

    @Transactional(rollbackFor = {Exception.class})
    public void addArticle(ArticleAddVo vo) {
        // 添加文章 设置 摘要 & 字数
        int wordNum = StringUtils.countMarkdownWords(vo.getContent());
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
        if(vo.getTagIdList() != null && !vo.getTagIdList().isEmpty()) {
            // 关联标签
            List<ArticleTag> articleTags = new ArrayList<>();
            vo.getTagIdList().forEach(item -> {
                articleTags.add(new ArticleTag(record.getId(), item));
            });
            articleTagDao.batchInsert(articleTags);
        }
    }

    public void modifyArticle(Article article) {
        // 文章字数
        if(article.getContent() != null) {
            String content =  article.getContent();
            // String digest  = StringUtils.generateDigest(content);
            int wordNum = StringUtils.countMarkdownWords(content);
            // article.setDigest(digest);
            article.setWordNum(wordNum);
        }
        articleDao.update(article);
    }

    public void removeArticle(Integer id) {
        articleDao.deleteById(id);
    }

    /**
     * 为文章添加标签信息
     * @param articleTag
     */
    public void addTagInfo(ArticleTag articleTag) {
        Integer articleId = articleTag.getArticleId();
        Integer tagId = articleTag.getTagId();
//        ArticleView article = articleDao.selectArticleById(articleId);
        Article article = articleDao.selectById(articleId);
        Tag tag = tagDao.selectTagById(tagId);
        Assert.notNull(article, "找不到文章 articleId:" + articleId);
        Assert.notNull(tag, "找不到标签 tagId:" + tagId);
        articleTagDao.insert(articleTag);
    }

    public void addCategoryInfo(ArticleCategory articleCategory) {
        Integer articleId = articleCategory.getArticleId();
        Integer categoryId = articleCategory.getCategoryId();
        Article article = articleDao.selectById(articleId);
        Category category = categoryDao.selectCategoryById(categoryId);
        Assert.notNull(article, "找不到文章 articleId:" + articleId);
        Assert.notNull(category, "找不到分类 categoryId:" + categoryId);
        articleCategoryDao.insert(articleCategory);
    }

    public List<ArticleView> getArticleInfoWithPage(ArticleVo vo) {
        executePage(vo);
        return articleDao.selectArticleWithPage(vo);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void setArticleCoverImage(MultipartFile file, Integer articleId) {
        if(checkFileValidation(file, articleId)) {
            try {
                String fileName = generateFilename() + "." + file.getContentType().split("/")[1];
                String filePath = coverPath + fileName;
                File directory = new File(coverPath);
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
                ArticleCover articleCover = new ArticleCover();
                articleCover.setArticleId(articleId);
                articleCover.setType(file.getContentType());
                articleCover.setSize(file.getSize());
                articleCover.setName(fileName);
                articleCover.setUrl(saveFile.getAbsolutePath());
                articleCoverDao.add(articleCover);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        }
    }
    @Transactional(rollbackFor = {Exception.class})
    public void updateArticleCoverImage(MultipartFile file, Integer articleId) {
        if(checkFileValidation(file, articleId)) {
            ArticleCover oldRecord = articleCoverDao.selectByArticleId(articleId);
            Assert.notNull(oldRecord, "没有找到该文章封面");
            try {
                String fileName = generateFilename() + "." + file.getContentType().split("/")[1];
                String filePath = coverPath + fileName;
                File needDelFile = new File(oldRecord.getUrl());
                if(!needDelFile.delete()) {
                    log.warn("文章图片无法删除 文件名：{}", needDelFile.getName());
                }
                File saveFile = new File(filePath);
                try (FileOutputStream fos = new FileOutputStream(saveFile)) {
                    fos.write(file.getBytes());
                }
                ArticleCover articleCover = new ArticleCover();
                articleCover.setArticleId(articleId);
                articleCover.setType(file.getContentType());
                articleCover.setSize(file.getSize());
                articleCover.setName(fileName);
                articleCover.setUrl(saveFile.getAbsolutePath());
                articleCoverDao.update(articleCover);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public ArticleImg updateArticleContentImg(MultipartFile file) {
        // 上传文章图片不需要articleId
        if(checkFileValidation(file, 1)) {
            ArticleImg articleImg = new ArticleImg();
            try {
                String fileName = generateFilename() + "." + file.getContentType().split("/")[1];
                // 处理上传文件为svg格式
                int idx = fileName.indexOf('+');
                if(idx != -1) {
                    fileName = fileName.substring(0, idx);
                }
                String filePath = imgPath + fileName;
                File saveFile = new File(filePath);
                try (FileOutputStream fos = new FileOutputStream(saveFile)) {
                    fos.write(file.getBytes());
                }
                articleImg.setType(file.getContentType());
                articleImg.setName(fileName);
                articleImg.setSize(file.getSize());
                articleImg.setUri(saveFile.getAbsolutePath());
                articleImgDao.add(articleImg);
                return articleImg;
            }catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        }
        return null;
    }

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

    private boolean checkFileValidation(MultipartFile file, Integer articleId) {
        if(articleId == null) {
            throw new ParameterException("文章id不能为空");
        }
        if (file.isEmpty()) {
            throw new ParameterException("上传的文件不能为空");
        }
        if (file.getContentType() == null) {
            throw new ParameterException("文件类型不能为空");
        }
        if (file.getSize() <= 0) {
            throw new ParameterException("文件大小不能为零");
        }
        switch (file.getContentType()) {
            case "image/jpeg", "image/png", "image/svg+xml" -> {
                return true;
            }
            default -> throw new ParameterException("文件类型不支持");
        }
    }
}
