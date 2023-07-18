package com.cystrix.blog.service;

import com.cystrix.blog.entity.Comment;

/**
 * @author: chenyue7@foxmail.com
 * @date: 18/7/2023
 * @description:
 */
public interface CommentService {

    void addComment(Comment comment);
    void removeComment(Integer id);

    Comment getCommentById(Integer id);
}
