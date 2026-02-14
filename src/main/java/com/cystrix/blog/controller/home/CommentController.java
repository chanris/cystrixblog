package com.cystrix.blog.controller.home;

import com.cystrix.blog.entity.Comment;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.query.CommentQuery;
import com.cystrix.blog.service.impl.CommentServiceImpl;
import com.cystrix.blog.vo.CommentAddVo;
import com.cystrix.blog.vo.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author chenyue7@foxmail.com
 * @date 2025/5/29
 * @description
 */
@Api(tags = "评论管理")
@RestController
@RequestMapping(value = "/home/comment")
public class CommentController {

    @Resource
    private CommentServiceImpl commentService;

    @ApiOperation(value = "获取评论列表")
    @PostMapping(value = "/list")
    public Response getCommentList(@ApiParam(value = "评论查询参数") @RequestBody CommentQuery query) {
        return Response.ok(commentService.commentTree(query.getArticleId()));
    }

    @ApiOperation(value = "添加评论")
    @PostMapping(value = "/add")
    public Response addComment(@ApiParam(value = "评论信息") @RequestBody CommentAddVo vo) {
        if (!StringUtils.hasText(vo.getContent()) || vo.getArticleId() == null) {
            throw new ParameterException("创建评论参数错误");
        }
        commentService.addComment(vo);
        return Response.ok();
    }
}