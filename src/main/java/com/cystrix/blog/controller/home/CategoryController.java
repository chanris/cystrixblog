package com.cystrix.blog.controller.home;

import com.cystrix.blog.entity.ArticleCategory;
import com.cystrix.blog.entity.Category;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.query.ArticleQuery;
import com.cystrix.blog.query.PageQuery;
import com.cystrix.blog.service.impl.CategoryServiceImpl;
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
@Api(tags = "前端分类管理")
@RestController
@RequestMapping(value = "/home/category")
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @ApiOperation(value = "根据文章ID获取分类")
    @RequestMapping(value = "/getCategoryByArticleId")
    public Response getCategoryByArticleId(@ApiParam(value = "文章查询参数") @RequestBody ArticleQuery articleQuery) {
        try {
            Assert.notNull(articleQuery.getId(), "文章id不能为空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        return Response.ok(categoryService.getCategoryByArticleId(articleQuery.getId()));
    }



    @ApiOperation(value = "获取分类树")
    @PostMapping(value = "/tree")
    public Response categoryTreeList(@ApiParam(value = "分类信息") @RequestBody Category category) {
        try {
            Assert.notNull(category.getId(), "分类id不能未空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        return Response.ok(categoryService.categoryTree(category.getId()));
    }

}