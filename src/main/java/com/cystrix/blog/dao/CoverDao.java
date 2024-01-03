package com.cystrix.blog.dao;

import com.cystrix.blog.entity.Cover;
import org.springframework.stereotype.Repository;

/**
 * @author chenyue7@foxmail.com
 * @date 29/12/2023
 * @description
 */
@Repository
public interface CoverDao {

    void add(Cover cover);
}
