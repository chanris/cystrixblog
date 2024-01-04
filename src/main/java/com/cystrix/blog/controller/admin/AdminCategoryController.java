package com.cystrix.blog.controller.admin;

import com.cystrix.blog.entity.ArticleCategory;
import com.cystrix.blog.entity.Category;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.query.CategoryQuery;
import com.cystrix.blog.service.impl.CategoryServiceImpl;
import com.cystrix.blog.vo.Response;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * @author: chenyue7@foxmail.com
 * @date: 13/7/2023
 * @description:
 */
@RestController
@RequestMapping(value = "/admin/category")
public class AdminCategoryController {

    private final CategoryServiceImpl categoryService;

    public AdminCategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(value = "/add")
    public Response create(@RequestBody Category category) {
        try {
            Assert.notNull(category.getName(), "分类名称不能为空");
            Assert.notNull(category.getPid(), "pid不能为空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        return Response.ok(categoryService.addCategory(category));
    }

    @PostMapping(value = "/update")
    public Response update(@RequestBody Category category) {
        try {
            Assert.notNull(category.getId(), "分类id不能空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        categoryService.modifyCategory(category);
        return Response.ok();
    }

    @PostMapping(value = "/updateCategoryRef")
    public Response updateCategoryRef(@RequestBody ArticleCategory articleCategory) {
        try {
            Assert.notNull(articleCategory.getCategoryId(), "分类id不能空");
            Assert.notNull(articleCategory.getArticleId(), "文章id不能空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        categoryService.modifyArticleCategory(articleCategory);
        return Response.ok();
    }

    @PostMapping(value = "/delete")
    public Response delete(@RequestBody CategoryQuery query) {
        try {
            Assert.notNull(query.getId(), "分类id不能空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        categoryService.deleteById(query.getId());
        return Response.ok();
    }

    @PostMapping(value = "/tree")
    public Response getCategoryTreeList(@RequestBody Category category) {
        try {
            Assert.notNull(category.getId(), "分类id不能未空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        return Response.ok(categoryService.categoryTree(category.getId()));
    }
}
