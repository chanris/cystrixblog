package com.cystrix.blog.dao;

import com.cystrix.blog.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
@Repository
public interface UserInfoDao {
    void insert( UserInfo userInfo);
}
