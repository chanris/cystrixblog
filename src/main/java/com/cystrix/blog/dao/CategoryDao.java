package com.cystrix.blog.dao;

import com.cystrix.blog.entity.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
@Repository
public interface CategoryDao {
    void insert(Category category);

    void update(Category category);

    void deleteById(Integer id);

    Category selectCategoryById(Integer id);
    Category selectCategoryByArticleId(Integer id);

    List<Category> categoryTree(Integer categoryId);
}
