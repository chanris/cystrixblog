package com.cystrix.blog.service.impl;

import com.cystrix.blog.dao.CategoryDao;
import com.cystrix.blog.entity.Category;
import com.cystrix.blog.query.PageQuery;
import com.cystrix.blog.service.CategoryService;
import com.cystrix.blog.view.CategoryView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author: chenyue7@foxmail.com
 * @date: 13/7/2023
 * @description:
 */
@Slf4j
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

    @Override
    public CategoryView categoryTree(Integer categoryId) {
        List<Category> categoryList = categoryDao.categoryTree(categoryId);
//        log.info("categorylist {}", Arrays.toString(categoryList.toArray()));
        Queue<CategoryView> queue = new LinkedList<>();
        CategoryView root = new CategoryView();
        Category category = categoryList.get(0);
        root.setLevel(category.getLevel());
        root.setId(category.getId());
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
}
