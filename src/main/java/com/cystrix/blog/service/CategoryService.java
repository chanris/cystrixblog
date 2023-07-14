package com.cystrix.blog.service;

import com.cystrix.blog.entity.Category;

/**
 * @author: chenyue7@foxmail.com
 * @date: 13/7/2023
 * @description:
 */
public interface CategoryService {

    void addCategory(Category category);

    void modifyCategory(Category category);

    void deleteById(Integer id);
}
