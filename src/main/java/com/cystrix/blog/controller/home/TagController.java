package com.cystrix.blog.controller.home;

import com.cystrix.blog.entity.Tag;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.query.ArticleQuery;
import com.cystrix.blog.query.PageQuery;
import com.cystrix.blog.service.impl.TagServiceImpl;
import com.cystrix.blog.vo.Response;
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
    public Response getPagedTagList(@RequestBody PageQuery query) {
        try {
            Assert.notNull(query.getPageSize(), "分页大小不能空");
            Assert.notNull(query.getPageNum(), "分页数不能为空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }

        List<Tag> pageTag = tagService.getPageTag(query);
        return Response.ok(pageTag);
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
