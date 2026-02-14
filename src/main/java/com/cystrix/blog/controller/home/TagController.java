package com.cystrix.blog.controller.home;

import com.cystrix.blog.entity.Tag;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.query.ArticleQuery;
import com.cystrix.blog.query.PageQuery;
import com.cystrix.blog.service.impl.TagServiceImpl;
import com.cystrix.blog.vo.BaseVo;
import com.cystrix.blog.vo.Response;
import com.github.pagehelper.PageInfo;
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
@Api(tags = "前端标签管理")
@RestController
@RequestMapping(value = "/home/tag")
public class TagController {
    private final TagServiceImpl tagService;

    public TagController(TagServiceImpl tagService) {
        this.tagService = tagService;
    }

    @ApiOperation(value = "获取分页标签列表")
    @RequestMapping(value = "/page")
    public Response getPagedTagList(@ApiParam(value = "分页参数") @RequestBody BaseVo vo) {
        return Response.ok(new PageInfo<>(tagService.getPageTag(vo)));
    }

    @ApiOperation(value = "获取所有标签")
    @GetMapping(value = "/all")
    public Response getAllTagList() {
        return Response.ok(tagService.getAll());
    }

    @ApiOperation(value = "根据文章ID获取标签列表")
    @RequestMapping(value = "/list")
    public Response getTagList(@ApiParam(value = "文章查询参数") @RequestBody ArticleQuery articleQuery) {
        try {
            Assert.notNull(articleQuery.getId(), "文章id不能为空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        List<Tag> tagList = tagService.getTagListByArticleId(articleQuery.getId());
        return Response.ok(tagList);
    }
}