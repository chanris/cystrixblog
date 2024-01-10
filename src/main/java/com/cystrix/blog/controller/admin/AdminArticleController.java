package com.cystrix.blog.controller.admin;

import com.cystrix.blog.entity.Article;
import com.cystrix.blog.entity.ArticleCategory;
import com.cystrix.blog.entity.ArticleTag;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.query.ArticleQuery;
import com.cystrix.blog.service.impl.ArticleServiceImpl;
import com.cystrix.blog.vo.ArticleAddVo;
import com.cystrix.blog.vo.ArticleVo;
import com.cystrix.blog.vo.Response;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: chenyue7@foxmail.com
 * @date: 12/7/2023
 * @description:
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin/article")
public class AdminArticleController {

    private final ArticleServiceImpl articleService;

    public AdminArticleController(ArticleServiceImpl articleService) {
        this.articleService = articleService;
    }

    /**
     * 添加文章
     * @param vo ArticleAddVo
     * @return
     */
    @PostMapping(value = "/add")
    public Response createArticle(@RequestBody ArticleAddVo vo) {
        try {
            Assert.notNull(vo.getTitle(), "文章标题不能为空");
            Assert.notNull(vo.getContent(), "文章内容不能为空");
            Assert.notNull(vo.getDigest(), "文章摘要不能为空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        articleService.addArticle(vo);
        return Response.ok();
    }

    /**
     * 更新文章信息
     * @param article
     * @return
     */
    @PostMapping(value = "/update")
    public Response updateArticle(@RequestBody Article article) {
        try {
            Assert.notNull(article.getId(), "文章id不能为空");
        }catch (Exception e){
            throw new ParameterException(e.getMessage());
        }
        articleService.modifyArticle(article);
        return Response.ok();
    }

    @PostMapping(value = "/remove")
    public Response removeArticle(@RequestBody ArticleQuery query) {
        try {
            Assert.notNull(query.getId(), "文章id不能为空");
        }catch (Exception e){
            throw new ParameterException(e.getMessage());
        }
        articleService.removeArticle(query.getId());
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

    @PostMapping(value = "/list")
    public Response getArticleInfoWithPage(@RequestBody ArticleVo  vo) {
        return Response.ok(new PageInfo<>(articleService.getArticleInfoWithPage(vo)));
    }

    @PostMapping(value = "/detail")
    public Response getArticleDetail(@RequestBody Article article) {
        try {
            Assert.notNull(article.getId(), "文章id不能为空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        return Response.ok(articleService.getDetailArticle(article.getId()));
    }

    /**
     * 上传文章内容图片
     * @param file
     * @param id
     * @return
     */
    @PostMapping(value = "/upload/img")
    public Response uploadImg(@RequestParam("file") MultipartFile file, @RequestParam("id") Integer id) {
        // todo 24/1/9
        return Response.ok();
    }

    // todo 23/12/29 上传的图片无法在服务器内删除
    /**
     * 上传文章封面图片
     * @param file
     * @param id
     * @return
     */
    @PostMapping(value = "/upload/cover")
    public Response uploadCover(@RequestParam("file") MultipartFile file, @RequestParam("id") Integer id) {
        articleService.setArticleCoverImage(file, id);
        return Response.ok();
    }

    @PostMapping(value = "/update/cover")
    public Response updateCover(@RequestParam("file") MultipartFile file, @RequestParam("id") Integer id) {
        articleService.updateArticleCoverImage(file, id);
        return Response.ok();
    }
}
