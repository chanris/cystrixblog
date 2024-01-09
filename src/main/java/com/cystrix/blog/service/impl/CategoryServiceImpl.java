package com.cystrix.blog.service.impl;

import com.cystrix.blog.dao.ArticleCategoryDao;
import com.cystrix.blog.dao.CategoryDao;
import com.cystrix.blog.entity.ArticleCategory;
import com.cystrix.blog.entity.Category;
import com.cystrix.blog.query.PageQuery;
import com.cystrix.blog.view.ArticleCategoryView;
import com.cystrix.blog.view.CategoryView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author: chenyue7@foxmail.com
 * @date: 13/7/2023
 * @description:
 */
@Slf4j
@Service
public class CategoryServiceImpl {

    private final CategoryDao categoryDao;

    private final ArticleCategoryDao articleCategoryDao;

    public CategoryServiceImpl(CategoryDao categoryDao, ArticleCategoryDao articleCategoryDao) {
        this.categoryDao = categoryDao;
        this.articleCategoryDao = articleCategoryDao;
    }

    public Integer addCategory(Category category) {
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        categoryDao.insert(category);
        return  category.getId();
    }

    public void modifyCategory(Category category) {
        categoryDao.update(category);
    }

    public void modifyArticleCategory(ArticleCategory articleCategory) {
        articleCategoryDao.update(articleCategory);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void deleteById(Integer id) {
        articleCategoryDao.deleteByCategoryId(id);
        categoryDao.deleteById(id);
    }

    public CategoryView categoryTree(Integer categoryId) {
        List<Category> categoryList = categoryDao.categoryTree(categoryId);
        Queue<CategoryView> queue = new LinkedList<>();
        CategoryView root = new CategoryView();
        Category category = categoryList.get(0);
        root.setLevel(category.getLevel());
        root.setId(category.getId());
        root.setArticleNum(category.getArticleNum());
        root.setName(category.getName());
        root.setLabel(category.getName());
        root.setValue(category.getId() + "");
        queue.offer(root);

        while (!queue.isEmpty()) {
            CategoryView crtNode = queue.poll();
            int id = crtNode.getId();
            int level = crtNode.getLevel();
            // 获得当前节点的所有子节点
            List<CategoryView> childrenList = new ArrayList<>();
            for (int i = 1; i < categoryList.size(); i ++) {
                Category c = categoryList.get(i);
                if (c.getLevel() == (level + 1) && c.getPid() != null && c.getPid() == id) {
                    CategoryView child = new CategoryView();
                    child.setName(c.getName());
                    child.setPid(c.getPid());
                    child.setId(c.getId());
                    child.setArticleNum(c.getArticleNum());
                    child.setLevel(c.getLevel());
                    child.setLabel(c.getName());
                    child.setValue(c.getId() + "");
                    childrenList.add(child);
                }
            }
            crtNode.setChildren(childrenList);
            queue.addAll(childrenList);
        }
        return root;
    }

    public Category getCategoryByArticleId(Integer articleId) {
        return categoryDao.selectCategoryByArticleId(articleId);
    }
}
