package com.cystrix.blog.dao;

import com.cystrix.blog.entity.Comment;
import org.springframework.stereotype.Repository;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
@Repository
public interface CommentDao {

    void insert(Comment comment);

    void deleteById(Integer id);

    Comment selectCommentById(Integer id);
}
