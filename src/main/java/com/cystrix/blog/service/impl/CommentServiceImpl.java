package com.cystrix.blog.service.impl;

import com.cystrix.blog.dao.CommentDao;
import com.cystrix.blog.entity.Comment;
import com.cystrix.blog.service.CommentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author: chenyue7@foxmail.com
 * @date: 18/7/2023
 * @description:
 */
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;

    public CommentServiceImpl(CommentDao commentDao) {
        this.commentDao = commentDao;
    }


    @Override
    public void addComment(Comment comment) {
        comment.setCreateTime(LocalDateTime.now());
        comment.setUpdateTime(LocalDateTime.now());
        commentDao.insert(comment);
    }

    @Override
    public void removeComment(Integer id) {
        commentDao.deleteById(id);
    }

    @Override
    public Comment getCommentById(Integer id) {
        return commentDao.selectCommentById(id);
    }
}
