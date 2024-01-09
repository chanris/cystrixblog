package com.cystrix.blog.service.impl;

import com.cystrix.blog.dao.ArticleTagDao;
import com.cystrix.blog.dao.TagDao;
import com.cystrix.blog.entity.ArticleTag;
import com.cystrix.blog.entity.Tag;
import com.cystrix.blog.service.BaseService;
import com.cystrix.blog.vo.BaseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: chenyue7@foxmail.com
 * @date: 13/7/2023
 * @description:
 */
@Slf4j
@Service
public class TagServiceImpl extends BaseService {

    private final TagDao tagDao;

    private final ArticleTagDao articleTagDao;

    public TagServiceImpl(TagDao tagDao, ArticleTagDao articleTagDao) {
        this.tagDao = tagDao;
        this.articleTagDao = articleTagDao;
    }

    public void addTag(Tag tag) {
        tagDao.insert(tag);
    }

    public void batchAddRef(List<ArticleTag> list) {
        articleTagDao.batchInsert(list);
    }

    public List<Tag> getPageTag(BaseVo vo) {
        executePage(vo);
        return tagDao.getAll();
    }

    public List<Tag> getAll() {
        return tagDao.getAll();
    }

    public List<Tag> getTagListByArticleId(Integer articleId) {
        List<Integer> tagIds = articleTagDao.selectTagIdListByArticleId(articleId);
        if (tagIds.size() != 0) {
            return tagDao.selectTagListByIds(tagIds);
        }
        return new ArrayList<>();
    }

    public void modifyTag(Tag tag) {
        tagDao.update(tag);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void deleteById(Integer id) {
        tagDao.deleteById(id);
        articleTagDao.deleteByTagId(id);
    }

    public void deleteRef(ArticleTag articleTag) {
        articleTagDao.deleteByArticleIdAndTagId(articleTag);
    }

}
