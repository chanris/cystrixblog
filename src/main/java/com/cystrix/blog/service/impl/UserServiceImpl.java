package com.cystrix.blog.service.impl;

import com.cystrix.blog.dao.UserInfoDao;
import com.cystrix.blog.entity.UserInfo;
import com.cystrix.blog.service.UserService;
import com.cystrix.blog.vo.LoginToken;
import org.springframework.stereotype.Service;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserInfoDao userInfoDao;

    public UserServiceImpl(UserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }

    @Override
    public LoginToken doLoginHandle(UserInfo userInfo) {

        return null;
    }
}
