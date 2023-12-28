package com.cystrix.blog.controller.home;

import com.cystrix.blog.entity.Tag;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.query.ArticleQuery;
import com.cystrix.blog.query.PageQuery;
import com.cystrix.blog.service.impl.TagServiceImpl;
import com.cystrix.blog.vo.BaseVo;
import com.cystrix.blog.vo.Response;
import com.github.pagehelper.PageInfo;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: chenyue7@foxmail.com
 * @date: 13/7/2023
 * @description:
 */
@RestController
@RequestMapping(value = "/home/tag")
public class TagController {
    private final TagServiceImpl tagService;

    public TagController(TagServiceImpl tagService) {
        this.tagService = tagService;
    }

    @RequestMapping(value = "/page")
    public Response getPagedTagList(@RequestBody BaseVo vo) {
        return Response.ok(new PageInfo<>(tagService.getPageTag(vo)));
    }

    @GetMapping(value = "/all")
    public Response getAllTagList() {
        return Response.ok(tagService.getAll());
    }

    @RequestMapping(value = "/list")
    public Response getTagList(@RequestBody ArticleQuery articleQuery) {
        try {
            Assert.notNull(articleQuery.getId(), "文章id不能为空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        List<Tag> tagList = tagService.getTagListByArticleId(articleQuery.getId());
        return Response.ok(tagList);
    }
}
