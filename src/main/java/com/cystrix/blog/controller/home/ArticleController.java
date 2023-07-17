package com.cystrix.blog.controller.home;

import com.cystrix.blog.entity.Article;
import com.cystrix.blog.enums.CodeEnum;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.query.ArticleQuery;
import com.cystrix.blog.query.PageQuery;
import com.cystrix.blog.query.PageQueryWithYear;
import com.cystrix.blog.query.TagQuery;
import com.cystrix.blog.service.impl.ArticleServiceImpl;
import com.cystrix.blog.vo.Response;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: chenyue7@foxmail.com
 * @date: 6/7/2023
 * @description:
 */
@RestController
@RequestMapping(value = "/home/article")
public class ArticleController {

    private final ArticleServiceImpl articleService;

    public ArticleController(ArticleServiceImpl articleService) {
        this.articleService = articleService;
    }

    /**
     * 文章摘要列表，首页展示
     */
    @RequestMapping(value = "/listArticleDigestInfo")
    public Response listArticleDigestInfo(@RequestBody PageQuery pageQuery) {
        try {
            Assert.notNull(pageQuery.getPageNum(), "查询页数不能为空");
            Assert.notNull(pageQuery.getPageSize(), "分页大小不能为空");
        }catch (Exception e){
            throw new ParameterException(e.getMessage());
        }
        List<Article> articles = articleService.getPagedArticleWithoutContent(pageQuery.getPageNum(), pageQuery.getPageSize());
        return Response.builder().code(CodeEnum.OK.code).msg("查询成功").data(articles).build();
    }

    /**
     * 根据年份获得文章摘要列表
     */
    @RequestMapping(value = "/listArchive")
    public Response listArchive(@RequestBody PageQueryWithYear pageQuery) {
        try {
            Assert.notNull(pageQuery.getPageNum(), "查询页数不能为空");
            Assert.notNull(pageQuery.getPageSize(), "分页大小不能为空");
            Assert.notNull(pageQuery.getPageSize(), "文章年份不能为空");
        }catch (Exception e){
            throw new ParameterException(e.getMessage());
        }
        List<Article> articles = articleService.getPagedArticleByYearWithoutContent(pageQuery.getPageNum(),
                pageQuery.getPageSize(), pageQuery.getYear());
        return Response.builder().code(CodeEnum.OK.code).msg("查询成功").data(articles).build();
    }

    /**
     * 文章详细页面
     */
    @RequestMapping(value = "/articleDetailInfo")
    public Response articleStatisticalInfo(@RequestBody ArticleQuery query) {
        try {
            Assert.notNull(query.getId(), "文章id不能为空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        Article article = articleService.getDetailArticle(query.getId());
        return Response.ok(article);
    }

    /**
     * 热门文章列表（默认10篇）
     */
    @RequestMapping(value = "/listHotArticle")
    public Response listHotArticle() {
        List<Article> articles = articleService.listArticleOrderByHotRank();
        return Response.ok(articles);
    }

    @RequestMapping(value = "/listByTag")
    public Response listArticleByTag(@RequestBody TagQuery query) {
        try {
            Assert.notNull(query.getId(), "tagId不能为空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        List<Article> articleDigestInfoByTagId = articleService.getArticleDigestInfoByTagId(query.getId());
        return Response.ok(articleDigestInfoByTagId);
    }



}
