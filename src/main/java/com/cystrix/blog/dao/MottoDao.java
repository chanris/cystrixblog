package com.cystrix.blog.dao;

import com.cystrix.blog.entity.Motto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author chenyue7@foxmail.com
 * @date 28/12/2023
 * @description
 */
@Repository
public interface MottoDao {
    List<Motto> getMottoListByRand(Integer num);
}
