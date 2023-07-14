package com.cystrix.blog.service.impl;

import com.cystrix.blog.dao.ArticleTagDao;
import com.cystrix.blog.dao.TagDao;
import com.cystrix.blog.entity.Tag;
import com.cystrix.blog.query.PageQuery;
import com.cystrix.blog.service.TagService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: chenyue7@foxmail.com
 * @date: 13/7/2023
 * @description:
 */
@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;

    private final ArticleTagDao articleTagDao;

    public TagServiceImpl(TagDao tagDao, ArticleTagDao articleTagDao) {
        this.tagDao = tagDao;
        this.articleTagDao = articleTagDao;
    }

    @Override
    public void addTag(Tag tag) {
        tag.setCreateTime(LocalDateTime.now());
        tag.setUpdateTime(LocalDateTime.now());
        tagDao.insert(tag);
    }

    @Override
    public List<Tag> getPageTag(PageQuery query) {
        return tagDao.selectPage(query.getPageSize(), (query.getPageNum() - 1) * query.getPageSize());
    }

    @Override
    public List<Tag> getTagListByArticleId(Integer articleId) {
        List<Integer> tagIds = articleTagDao.selectTagIdListByArticleId(articleId);
        if (tagIds.size() != 0) {
            return tagDao.selectTagListByIds(tagIds);
        }
        return new ArrayList<>();
    }

    @Override
    public void modifyTag(Tag tag) {
        tag.setUpdateTime(LocalDateTime.now());
        tagDao.update(tag);
    }

    @Override
    public void deleteById(Integer id) {
        tagDao.deleteById(id);
    }
}
