package com.cystrix.blog.service.impl;

import com.cystrix.blog.dao.UserInfoDao;
import com.cystrix.blog.entity.UserInfo;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.service.UserService;
import com.cystrix.blog.util.JwtUtils;
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
    private final JwtUtils jwtUtils;

    public UserServiceImpl(UserInfoDao userInfoDao, JwtUtils jwtUtils) {
        this.userInfoDao = userInfoDao;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public LoginToken doLoginHandle(UserInfo userInfo) {
        UserInfo result = userInfoDao.queryUserInfoByUsernameAndPassword(userInfo);
        if (result == null) {
            throw new ParameterException("用户名密码错误");
        }
        return new LoginToken(result.getId(), result.getUsername(), jwtUtils.createTokenByUser(result));
    }
}
