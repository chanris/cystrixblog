package com.cystrix.blog.service;

import com.cystrix.blog.entity.UserInfo;
import com.cystrix.blog.vo.LoginToken;
import com.cystrix.blog.vo.LoginVo;
import com.cystrix.blog.view.UserInfoView;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
public interface UserService {

    LoginToken doLoginHandle(LoginVo loginVo);

    UserInfoView getUserInfoVo();

    void addUserInfo(UserInfo userInfo);

    void modifyUserInfo(UserInfo userInfo);

    void removeUserInfo(Integer id);

    boolean isExistedUser(String email);
}
