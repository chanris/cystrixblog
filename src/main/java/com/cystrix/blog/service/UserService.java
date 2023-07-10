package com.cystrix.blog.service;

import com.cystrix.blog.entity.UserInfo;
import com.cystrix.blog.vo.LoginToken;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
public interface UserService {

    LoginToken doLoginHandle(UserInfo userInfo);
}
