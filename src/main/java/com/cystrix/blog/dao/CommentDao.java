package com.cystrix.blog.dao;

import com.cystrix.blog.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 2025/5/29
 * @description
 */
@Repository
public interface CommentDao {
    void insert(Comment comment);

    // void update(Comment comment);

    List<Comment> commentListByArticleId(Integer articleId);
}
