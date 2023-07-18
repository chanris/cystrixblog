package com.cystrix.blog.controller.home;

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
@RequestMapping(value = "/home/comment")
public class CommentController {

    private final CommentServiceImpl commentService;

    private final NetUtils netUtils;

    public CommentController(CommentServiceImpl commentService, NetUtils netUtils)  {
        this.commentService = commentService;
        this.netUtils = netUtils;
    }

    /**
     * 游客添加评论
     */
    @RequestMapping(value = "/add")
    public Response addComment(@RequestBody Comment comment, HttpServletRequest request) {
        try {
            Assert.notNull(comment.getContent(), "评论内容不能为空");
            if (comment.getUserIp() == null) {
                String ip = netUtils.getIpAddress(request);
                comment.setUserIp(ip);
            }
            Assert.notNull(comment.getUserIp(), "评论游客ip地址不能为空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        commentService.addComment(comment);
        return  Response.ok("创建成功");
    }

    @RequestMapping(value = "")
    public Response getCommentInfo(@RequestBody CommentQuery query) {
        try {
            Assert.notNull(query.getId(), "id不能为空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        Comment commentById = commentService.getCommentById(query.getId());
        return Response.ok(commentById);
    }
}
