package com.cystrix.blog.controller.admin;

import com.cystrix.blog.entity.Article;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.service.impl.ArticleServiceImpl;
import com.cystrix.blog.vo.Response;
import org.springframework.util.Assert;
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

    @RequestMapping(value = "/update")
    public Response updateArticle(@RequestBody Article article) {
        try {
            Assert.notNull(article.getId(), "文章id不能为空");
        }catch (Exception e){
            throw new ParameterException(e.getMessage());
        }
        this.articleService.modifyArticle(article);
        return Response.ok();
    }

}
