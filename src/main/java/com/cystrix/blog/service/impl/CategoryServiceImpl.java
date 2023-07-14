package com.cystrix.blog.service.impl;

import com.cystrix.blog.dao.CategoryDao;
import com.cystrix.blog.entity.Category;
import com.cystrix.blog.service.CategoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
}
