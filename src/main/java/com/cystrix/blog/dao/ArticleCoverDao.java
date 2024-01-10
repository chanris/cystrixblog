package com.cystrix.blog.dao;

import com.cystrix.blog.entity.ArticleCover;
import org.springframework.stereotype.Repository;

/**
 * @author chenyue7@foxmail.com
 * @date 29/12/2023
 * @description
 */
@Repository
public interface ArticleCoverDao {

    ArticleCover selectByArticleId(Integer id);
    void add(ArticleCover articleCover);
    void update(ArticleCover articleCover);
}
