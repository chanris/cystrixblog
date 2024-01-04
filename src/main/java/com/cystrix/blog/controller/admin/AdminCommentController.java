package com.cystrix.blog.controller.admin;

import com.cystrix.blog.entity.Comment;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.query.CommentQuery;
import com.cystrix.blog.service.impl.CommentServiceImpl;
import com.cystrix.blog.util.NetUtils;
import com.cystrix.blog.vo.Response;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: chenyue7@foxmail.com
 * @date: 18/7/2023
 * @description:
 */
@RestController
@RequestMapping(value = "/admin/comment")
public class AdminCommentController {

    private final CommentServiceImpl commentService;

    private final NetUtils netUtils;

    public AdminCommentController(CommentServiceImpl commentService,  NetUtils netUtils) {
        this.commentService = commentService;
        this.netUtils = netUtils;
    }

    @PostMapping(value = "/delete")
    public Response deleteComment(@RequestBody CommentQuery query) {
        try {
            Assert.notNull(query.getId(), "commentId不能为空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        commentService.removeComment(query.getId());
        return Response.ok();
    }
}
