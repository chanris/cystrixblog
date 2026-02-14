package com.cystrix.blog.controller.admin;

import com.cystrix.blog.entity.ArticleTag;
import com.cystrix.blog.entity.Tag;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.query.TagQuery;
import com.cystrix.blog.service.impl.TagServiceImpl;
import com.cystrix.blog.vo.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: chenyue7@foxmail.com
 * @date: 13/7/2023
 * @description:
 */
@Api(tags = "后台标签管理")
@RestController
@RequestMapping(value = "/admin/tag")
public class AdminTagController {

    private final TagServiceImpl tagService;

    public AdminTagController(TagServiceImpl tagService) {
        this.tagService = tagService;
    }

    @ApiOperation(value = "添加标签")
    @PostMapping(value = "/add")
    public Response createTag(@ApiParam(value = "标签信息") @RequestBody Tag tag) {
        try {
            Assert.notNull(tag.getName(), "标签名称不能为空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        tagService.addTag(tag);
        return Response.ok(tag.getId());
    }

    @ApiOperation(value = "更新标签")
    @PostMapping(value = "/update")
    public Response updateTag(@ApiParam(value = "标签信息") @RequestBody Tag tag) {
        try {
            Assert.notNull(tag.getId(), "标签id不能为空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        tagService.modifyTag(tag);
        return Response.ok();
    }

    @ApiOperation(value = "删除标签")
    @PostMapping(value = "/delete")
    public Response updateTag(@ApiParam(value = "标签查询参数") @RequestBody TagQuery query) {
        try {
            Assert.notNull(query.getId(), "标签id不能为空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        tagService.deleteById(query.getId());
        return Response.ok();
    }

    @ApiOperation(value = "删除文章标签关联")
    @PostMapping(value = "/deleteRef")
    public Response deleteRef(@ApiParam(value = "文章标签关联信息") @RequestBody ArticleTag articleTag) {
        try {
            Assert.notNull(articleTag.getTagId(), "标签id不能为空");
            Assert.notNull(articleTag.getArticleId(), "文章id不能为空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        tagService.deleteRef(articleTag);
        return Response.ok();
    }

    @ApiOperation(value = "批量添加文章标签关联")
    @PostMapping(value = "/batchAddRef")
    public Response batchAddRef(@ApiParam(value = "文章标签关联信息列表") @RequestBody List<ArticleTag> articleTag) {
        tagService.batchAddRef(articleTag);
        return Response.ok();
    }

}