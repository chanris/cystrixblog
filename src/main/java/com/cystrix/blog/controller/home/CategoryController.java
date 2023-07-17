package com.cystrix.blog.controller.home;

import com.cystrix.blog.entity.Category;
import com.cystrix.blog.entity.Tag;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.query.ArticleQuery;
import com.cystrix.blog.query.PageQuery;
import com.cystrix.blog.service.impl.CategoryServiceImpl;
import com.cystrix.blog.vo.Response;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: chenyue7@foxmail.com
 * @date: 13/7/2023
 * @description:
 */
@RestController
@RequestMapping(value = "/home/category")
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/page")
    public Response getPagedTagList(@RequestBody PageQuery query) {
        try {
            Assert.notNull(query.getPageSize(), "分页大小不能空");
            Assert.notNull(query.getPageNum(), "分页数不能为空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }

        List<Category> categoryList = categoryService.getPageTag(query);
        return Response.ok(categoryList);
    }

    @RequestMapping(value = "/list")
    public Response getTagList(@RequestBody ArticleQuery articleQuery) {
        try {
            Assert.notNull(articleQuery.getId(), "文章id不能为空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        List<Category> categoryList = categoryService.getTagListByArticleId(articleQuery.getId());
        return Response.ok(categoryList);
    }
}
