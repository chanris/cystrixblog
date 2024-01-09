package com.cystrix.blog.controller.home;

import com.cystrix.blog.entity.Article;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.query.*;
import com.cystrix.blog.service.impl.ArticleServiceImpl;
import com.cystrix.blog.view.ArticleView;
import com.cystrix.blog.vo.BaseVo;
import com.cystrix.blog.vo.Response;
import com.github.pagehelper.PageInfo;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

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
    public Response listArticleDigestInfo(@RequestBody BaseVo vo) {
        List<ArticleView> articles = articleService.getPagedArticleWithoutContent(vo);
        return Response.ok(new PageInfo<>(articles));
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
        ArticleView article = articleService.getDetailArticle(query.getId());
        return Response.ok(article);
    }

    /**
     * 文章点赞
     */
    @PostMapping(value = "/likeArticle")
    public Response likeArticle(@RequestBody ArticleQuery query) {
        try {
            Assert.notNull(query.getId(), "文章id不能为空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        articleService.addLikeCount(query.getId());
        return Response.ok();
    }

}
