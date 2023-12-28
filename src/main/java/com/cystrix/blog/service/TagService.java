package com.cystrix.blog.service;

import com.cystrix.blog.entity.ArticleTag;
import com.cystrix.blog.entity.Tag;
import com.cystrix.blog.query.PageQuery;
import com.cystrix.blog.vo.BaseVo;

import java.util.List;

/**
 * @author: chenyue7@foxmail.com
 * @date: 13/7/2023
 * @description:
 */
public interface TagService {

    void addTag(Tag tag);

    void batchAddRef(List<ArticleTag> list);

    List<Tag> getPageTag(BaseVo vo);

    List<Tag>  getAll();

    List<Tag> getTagListByArticleId(Integer articleId);

    void modifyTag(Tag tag);

    void deleteById(Integer id);

    void deleteRef(ArticleTag articleTag);

    void addRef(ArticleTag articleTag);
}
