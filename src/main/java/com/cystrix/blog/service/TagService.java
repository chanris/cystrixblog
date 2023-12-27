package com.cystrix.blog.service;

import com.cystrix.blog.entity.ArticleTag;
import com.cystrix.blog.entity.Tag;
import com.cystrix.blog.query.PageQuery;

import java.util.List;

/**
 * @author: chenyue7@foxmail.com
 * @date: 13/7/2023
 * @description:
 */
public interface TagService {

    void addTag(Tag tag);

    List<Tag> getPageTag(PageQuery query);

    List<Tag>  getAll();

    List<Tag> getTagListByArticleId(Integer articleId);

    void modifyTag(Tag tag);

    void deleteById(Integer id);

    void deleteRef(ArticleTag articleTag);

}
