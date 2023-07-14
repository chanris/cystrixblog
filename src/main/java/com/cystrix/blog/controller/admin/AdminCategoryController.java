package com.cystrix.blog.controller.admin;

import com.cystrix.blog.entity.Category;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.query.CategoryQuery;
import com.cystrix.blog.service.impl.CategoryServiceImpl;
import com.cystrix.blog.vo.Response;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        categoryService.addCategory(category);
        return Response.ok();
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
}
