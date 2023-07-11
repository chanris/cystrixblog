package com.cystrix.blog.service;

import com.cystrix.blog.entity.UserInfo;
import com.cystrix.blog.vo.LoginToken;
import com.cystrix.blog.vo.UserInfoVo;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
public interface UserService {

    LoginToken doLoginHandle(UserInfo userInfo);

    UserInfoVo getUserInfoVoById(Integer userId);
}
