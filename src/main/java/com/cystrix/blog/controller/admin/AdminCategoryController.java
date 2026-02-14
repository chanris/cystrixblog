package com.cystrix.blog.controller.admin;

import com.cystrix.blog.entity.ArticleCategory;
import com.cystrix.blog.entity.Category;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.query.CategoryQuery;
import com.cystrix.blog.service.impl.CategoryServiceImpl;
import com.cystrix.blog.vo.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * @author: chenyue7@foxmail.com
 * @date: 13/7/2023
 * @description:
 */
@Api(tags = "后台分类管理")
@RestController
@RequestMapping(value = "/admin/category")
public class AdminCategoryController {

    private final CategoryServiceImpl categoryService;

    public AdminCategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @ApiOperation(value = "添加分类")
    @PostMapping(value = "/add")
    public Response create(@ApiParam(value = "分类信息") @RequestBody Category category) {
        try {
            Assert.notNull(category.getName(), "分类名称不能为空");
            Assert.notNull(category.getPid(), "pid不能为空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        return Response.ok(categoryService.addCategory(category));
    }

    @ApiOperation(value = "更新分类")
    @PostMapping(value = "/update")
    public Response update(@ApiParam(value = "分类信息") @RequestBody Category category) {
        try {
            Assert.notNull(category.getId(), "分类id不能空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        categoryService.modifyCategory(category);
        return Response.ok();
    }

    @ApiOperation(value = "更新文章分类关联")
    @PostMapping(value = "/updateCategoryRef")
    public Response updateCategoryRef(@ApiParam(value = "文章分类关联信息") @RequestBody ArticleCategory articleCategory) {
        try {
            Assert.notNull(articleCategory.getCategoryId(), "分类id不能空");
            Assert.notNull(articleCategory.getArticleId(), "文章id不能空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        categoryService.modifyArticleCategory(articleCategory);
        return Response.ok();
    }

    @ApiOperation(value = "删除分类")
    @PostMapping(value = "/delete")
    public Response delete(@ApiParam(value = "分类查询参数") @RequestBody CategoryQuery query) {
        try {
            Assert.notNull(query.getId(), "分类id不能空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        categoryService.deleteById(query.getId());
        return Response.ok();
    }

    @ApiOperation(value = "获取分类树")
    @PostMapping(value = "/tree")
    public Response getCategoryTreeList(@ApiParam(value = "分类信息") @RequestBody Category category) {
        try {
            Assert.notNull(category.getId(), "分类id不能未空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        return Response.ok(categoryService.categoryTree(category.getId()));
    }
}