package com.cystrix.blog.service.impl;

import com.cystrix.blog.dao.CategoryDao;
import com.cystrix.blog.entity.Category;
import com.cystrix.blog.entity.Tag;
import com.cystrix.blog.query.PageQuery;
import com.cystrix.blog.service.CategoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: chenyue7@foxmail.com
 * @date: 13/7/2023
 * @description:
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public void addCategory(Category category) {
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        categoryDao.insert(category);
    }

    @Override
    public void modifyCategory(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        categoryDao.update(category);
    }

    @Override
    public void deleteById(Integer id) {
        categoryDao.deleteById(id);
    }

    @Override
    public List<Category> getPageTag(PageQuery query) {
        Integer offset = (query.getPageNum() - 1) * query.getPageSize();
        return  categoryDao.selectPage(query.getPageSize(), offset);
    }

    @Override
    public List<Category> getTagListByArticleId(Integer articleId) {

        return null;
    }
}
