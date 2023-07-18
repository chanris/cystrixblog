package com.cystrix.blog.controller.home;

import com.cystrix.blog.service.impl.CommentServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: chenyue7@foxmail.com
 * @date: 18/7/2023
 * @description:
 */
@RestController
@RequestMapping(value = "/home/comment")
public class CommentController {

    private final CommentServiceImpl commentService;

    public CommentController(CommentServiceImpl commentService)  {
        this.commentService = commentService;
    }
}
