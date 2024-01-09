package com.cystrix.blog.dao;

import com.cystrix.blog.entity.UserInfo;
import com.cystrix.blog.view.UserInfoView;
import org.springframework.stereotype.Repository;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
@Repository
public interface UserInfoDao {
    void insert(UserInfo userInfo);
    UserInfo selectUserInfoByEmail(String email);
    UserInfo selectUserInfoByUsernameAndPassword(UserInfo userInfo);
    UserInfo selectUserInfoByEmailAndPassword(UserInfo userInfo);
    UserInfo selectUserInfoById(Integer id);
    UserInfoView selectUserInfoView();
    void update(UserInfo userInfo);
}
