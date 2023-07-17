package com.cystrix.blog.dao;

import com.cystrix.blog.entity.ArticleTag;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
@Repository
public interface ArticleTagDao {

    void insert(ArticleTag articleTag);

    List<Integer> selectTagIdListByArticleId(Integer articleId);

    List<Integer> selectArticleIdByTagId(Integer tagId);

    void deleteByArticleIdAndTagId(ArticleTag articleTag);
}
