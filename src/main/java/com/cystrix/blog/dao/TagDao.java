package com.cystrix.blog.dao;

import com.cystrix.blog.entity.Tag;
import com.cystrix.blog.query.PageQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
@Repository
public interface TagDao {

    void insert(Tag tag);

    List<Tag> selectPage(@Param("pageSize") Integer pageSize, @Param("offset") Integer offset);

    List<Tag> selectTagListByIds(@Param("ids") List<Integer> ids);

    Tag selectTagById(Integer id);

    void update(Tag tag);

    void deleteById(Integer id);

   List<Tag> getAll();
}
