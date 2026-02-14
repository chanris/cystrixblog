package com.cystrix.blog.controller.admin;

import com.cystrix.blog.entity.Article;
import com.cystrix.blog.entity.ArticleCategory;
import com.cystrix.blog.entity.ArticleImg;
import com.cystrix.blog.entity.ArticleTag;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.query.ArticleQuery;
import com.cystrix.blog.service.impl.ArticleServiceImpl;
import com.cystrix.blog.vo.ArticleAddVo;
import com.cystrix.blog.vo.ArticleVo;
import com.cystrix.blog.vo.Response;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: chenyue7@foxmail.com
 * @date: 12/7/2023
 * @description:
 */
@Api(tags = "后台文章管理")
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
    @ApiOperation(value = "添加文章")
    @PostMapping(value = "/add")
    public Response createArticle(@ApiParam(value = "文章添加信息") @RequestBody ArticleAddVo vo) {
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
    @ApiOperation(value = "更新文章")
    @PostMapping(value = "/update")
    public Response updateArticle(@ApiParam(value = "文章信息") @RequestBody Article article) {
        try {
            Assert.notNull(article.getId(), "文章id不能为空");
        }catch (Exception e){
            throw new ParameterException(e.getMessage());
        }
        articleService.modifyArticle(article);
        return Response.ok();
    }

    @ApiOperation(value = "删除文章")
    @PostMapping(value = "/remove")
    public Response removeArticle(@ApiParam(value = "文章查询参数") @RequestBody ArticleQuery query) {
        try {
            Assert.notNull(query.getId(), "文章id不能为空");
        }catch (Exception e){
            throw new ParameterException(e.getMessage());
        }
        articleService.removeArticle(query.getId());
        return Response.ok();
    }

    @ApiOperation(value = "添加文章标签关联")
    @PostMapping(value = "/addTagInfo")
    public Response addTagInfo(@ApiParam(value = "文章标签关联信息") @RequestBody ArticleTag articleTag) {
        try {
            Assert.notNull(articleTag.getArticleId(), "文章id不能为空");
            Assert.notNull(articleTag.getTagId(), "标签id不能为空");
        }catch (Exception e){
            throw new ParameterException(e.getMessage());
        }
        articleService.addTagInfo(articleTag);
        return Response.ok();
    }

    @ApiOperation(value = "添加文章分类关联")
    @PostMapping(value = "/addCategoryInfo")
    public Response addCategoryInfo(@ApiParam(value = "文章分类关联信息") @RequestBody ArticleCategory articleCategory) {
        try {
            Assert.notNull(articleCategory.getArticleId(), "文章id不能为空");
            Assert.notNull(articleCategory.getCategoryId(), "分类id不能为空");
        }catch (Exception e){
            throw new ParameterException(e.getMessage());
        }
        articleService.addCategoryInfo(articleCategory);
        return Response.ok();
    }

    @ApiOperation(value = "获取文章列表")
    @PostMapping(value = "/list")
    public Response getArticleInfoWithPage(@ApiParam(value = "文章查询参数") @RequestBody ArticleVo  vo) {
        return Response.ok(new PageInfo<>(articleService.getArticleInfoWithPage(vo)));
    }

    @ApiOperation(value = "获取文章详情")
    @PostMapping(value = "/detail")
    public Response getArticleDetail(@ApiParam(value = "文章信息") @RequestBody Article article) {
        try {
            Assert.notNull(article.getId(), "文章id不能为空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        return Response.ok(articleService.getDetailArticle(article.getId()));
    }

    /**
     * 上传文章封面图片
     * @param file
     * @param id
     * @return
     */
    @ApiOperation(value = "上传文章封面图片")
    @PostMapping(value = "/upload/cover")
    public Response uploadCover(@ApiParam(value = "封面图片文件") @RequestParam("file") MultipartFile file, @ApiParam(value = "文章ID") @RequestParam("id") Integer id) {
        articleService.setArticleCoverImage(file, id);
        return Response.ok();
    }

    @ApiOperation(value = "更新文章封面图片")
    @PostMapping(value = "/update/cover")
    public Response updateCover(@ApiParam(value = "封面图片文件") @RequestParam("file") MultipartFile file, @ApiParam(value = "文章ID") @RequestParam("id") Integer id) {
        articleService.updateArticleCoverImage(file, id);
        return Response.ok();
    }

    /**
     * 上传文章图片
     * @param file
     * @param id
     * @return
     */
    @ApiOperation(value = "上传文章内容图片")
    @PostMapping(value = "/upload/img")
    public Response uploadArticleImg(@ApiParam(value = "文章内容图片文件") @RequestParam("file") MultipartFile file) {
        ArticleImg articleImg = articleService.updateArticleContentImg(file);
        return Response.ok(articleImg);
    }
}