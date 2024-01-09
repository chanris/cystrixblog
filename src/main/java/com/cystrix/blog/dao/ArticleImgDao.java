package com.cystrix.blog.dao;

import com.cystrix.blog.entity.ArticleImg;
import org.springframework.stereotype.Repository;

/**
 * @author chenyue7@foxmail.com
 * @date 5/1/2024
 * @description
 */
@Repository
public interface ArticleImgDao {
    void add(ArticleImg articleImg);
}

