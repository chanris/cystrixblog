package com.cystrix.blog.dao;

import com.cystrix.blog.entity.ArticleComment;
import org.springframework.stereotype.Repository;

/**
 * @author chenyue7@foxmail.com
 * @date 2025/6/1
 * @description
 */
@Repository
public interface ArticleCommentDao {

    void insert(ArticleComment articleComment);
}
