package com.cystrix.blog.controller.admin;

import com.cystrix.blog.entity.Article;
import com.cystrix.blog.entity.ArticleCategory;
import com.cystrix.blog.entity.ArticleTag;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.query.ArticleQuery;
import com.cystrix.blog.service.impl.ArticleServiceImpl;
import com.cystrix.blog.vo.Response;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: chenyue7@foxmail.com
 * @date: 12/7/2023
 * @description:
 */
@RestController
@RequestMapping(value = "/admin/article")
public class AdminArticleController {

    private final ArticleServiceImpl articleService;

    public AdminArticleController(ArticleServiceImpl articleService) {
        this.articleService = articleService;
    }

    @PostMapping(value = "/add")
    public Response createArticle(@RequestBody Article article) {
        try {
            Assert.notNull(article.getTitle(), "文章标题不能为空");
            Assert.notNull(article.getDigest(), "文章摘要不能为空");
            Assert.notNull(article.getContent(), "文章内容不能为空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        articleService.addArticle(article);
        return Response.ok();
    }

    @PostMapping(value = "/update")
    public Response updateArticle(@RequestBody Article article) {
        try {
            Assert.notNull(article.getId(), "文章id不能为空");
        }catch (Exception e){
            throw new ParameterException(e.getMessage());
        }
        this.articleService.modifyArticle(article);
        return Response.ok();
    }

    @PostMapping(value = "/remove")
    public Response removeArticle(@RequestBody ArticleQuery query) {
        try {
            Assert.notNull(query.getId(), "文章id不能为空");
        }catch (Exception e){
            throw new ParameterException(e.getMessage());
        }
        this.articleService.removeArticle(query.getId());
        return Response.ok();
    }

    @PostMapping(value = "/addTagInfo")
    public Response addTagInfo(@RequestBody ArticleTag articleTag) {
        try {
            Assert.notNull(articleTag.getArticleId(), "文章id不能为空");
            Assert.notNull(articleTag.getTagId(), "标签id不能为空");
        }catch (Exception e){
            throw new ParameterException(e.getMessage());
        }
        articleService.addTagInfo(articleTag);
        return Response.ok();
    }

    @PostMapping(value = "/addCategoryInfo")
    public Response addCategoryInfo(@RequestBody ArticleCategory articleCategory) {
        try {
            Assert.notNull(articleCategory.getArticleId(), "文章id不能为空");
            Assert.notNull(articleCategory.getCategoryId(), "分类id不能为空");
        }catch (Exception e){
            throw new ParameterException(e.getMessage());
        }
        articleService.addCategoryInfo(articleCategory);
        return Response.ok();
    }

}
