package com.cystrix.blog.controller.admin;

import com.cystrix.blog.entity.ArticleTag;
import com.cystrix.blog.entity.Tag;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.query.TagQuery;
import com.cystrix.blog.service.impl.TagServiceImpl;
import com.cystrix.blog.vo.Response;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * @author: chenyue7@foxmail.com
 * @date: 13/7/2023
 * @description:
 */
@RestController
@RequestMapping(value = "/admin/tag")
public class AdminTagController {

    private final TagServiceImpl tagService;

    public AdminTagController(TagServiceImpl tagService) {
        this.tagService = tagService;
    }

    @PostMapping(value = "/add")
    public Response createTag(@RequestBody Tag tag) {
        try {
            Assert.notNull(tag.getName(), "标签名称不能为空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        tagService.addTag(tag);
        return Response.ok(tag.getId());
    }

    @PostMapping(value = "/update")
    public Response updateTag(@RequestBody Tag tag) {
        try {
            Assert.notNull(tag.getId(), "标签id不能为空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        tagService.modifyTag(tag);
        return Response.ok();
    }

    @PostMapping(value = "/delete")
    public Response updateTag(@RequestBody TagQuery query) {
        try {
            Assert.notNull(query.getId(), "标签id不能为空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        tagService.deleteById(query.getId());
        return Response.ok();
    }

    @PostMapping(value = "/deleteRef")
    public Response deleteRef(@RequestBody ArticleTag articleTag) {
        try {
            Assert.notNull(articleTag.getTagId(), "标签id不能为空");
            Assert.notNull(articleTag.getArticleId(), "文章id不能为空");
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        tagService.deleteRef(articleTag);
        return Response.ok();
    }
}
