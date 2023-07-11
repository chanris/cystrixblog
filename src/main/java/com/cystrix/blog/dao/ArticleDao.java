package com.cystrix.blog.dao;

import com.cystrix.blog.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
@Repository
public interface ArticleDao {
    void insert(Article article);

    /*根据createTime排序*/
    List<Article> queryArticleByPage(@Param("pageSize") int pageSize, @Param("offset") int offset);
}
