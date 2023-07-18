package com.cystrix.blog.service.impl;

import com.cystrix.blog.dao.UserInfoDao;
import com.cystrix.blog.entity.UserInfo;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.service.UserService;
import com.cystrix.blog.util.JwtUtils;
import com.cystrix.blog.util.MD5Utils;
import com.cystrix.blog.util.RedisUtils;
import com.cystrix.blog.vo.LoginToken;
import com.cystrix.blog.vo.LoginVo;
import com.cystrix.blog.vo.UserInfoVo;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserInfoDao userInfoDao;
    private final JwtUtils jwtUtils;
    private final MD5Utils md5Utils;
    private final RedisUtils redisUtils;

    public UserServiceImpl(UserInfoDao userInfoDao, JwtUtils jwtUtils, MD5Utils md5Utils, RedisUtils redisUtils) {
        this.userInfoDao = userInfoDao;
        this.jwtUtils = jwtUtils;
        this.md5Utils = md5Utils;
        this.redisUtils = redisUtils;
    }

    @Override
    public LoginToken doLoginHandle(LoginVo loginVo) {
        String email = loginVo.getEmail();
        String redisKey = "LOGIN_CODE_" + email;
        // 验证邮箱验证码
        if (!(redisUtils.isExistedKey(redisKey) &&
                redisUtils.getValue(redisKey).equals(loginVo.getVerificationCode()))) {
            throw new ParameterException("验证码错误");
        }

        String origin = loginVo.getPassword();
        String encryption = md5Utils.encryption(origin);
        UserInfo userInfo = new UserInfo();
        userInfo.setPassword(encryption);
        userInfo.setEmail(email);
        UserInfo result = userInfoDao.selectUserInfoByEmailAndPassword(userInfo);
        if (result == null) {
            throw new ParameterException("账户密码错误");
        }
        // 登录成功，清除缓存
        redisUtils.deleteKey(redisKey);
        return new LoginToken(result.getId(), result.getUsername(), jwtUtils.createTokenByUser(result));
    }

    @Override
    public UserInfoVo getUserInfoVo(Integer userId) {
        UserInfo userInfo = userInfoDao.selectUserInfoById(userId);
        if (userId == null) {
            return null;
        }
        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setEmail(userInfo.getEmail());
        userInfoVo.setNickname(userInfo.getNickname());
        userInfoVo.setAvatar(userInfoVo.getAvatar());
        userInfoVo.setMotto(userInfo.getMotto());
        return userInfoVo;
    }

    @Override
    public void modifyUserInfo(UserInfo userInfo) {
        userInfo.setUpdateTime(LocalDateTime.now());
        String origin = userInfo.getPassword();
        if (origin != null) {
           userInfo.setPassword(md5Utils.encryption(origin));
        }
        userInfoDao.update(userInfo);
    }

    @Override
    public void removeUserInfo(Integer id) {
        userInfoDao.deleteById(id);
    }

    @Override
    public void addUserInfo(UserInfo userInfo) {
        // 加密密码
        String encryptionPasswd = md5Utils.encryption(userInfo.getPassword());
        userInfo.setPassword(encryptionPasswd);
        userInfo.setMotto("我是一只小懒猫 喵喵~~");
        userInfo.setAvatar("/upload/avatar/251231245.jpg");
        userInfo.setNickname(userInfo.getUsername());
        userInfo.setCreateTime(LocalDateTime.now());
        userInfo.setUpdateTime(LocalDateTime.now());
        userInfoDao.insert(userInfo);
    }

    @Override
    public boolean isExistedUser(String email) {
        return userInfoDao.selectUserInfoByEmail(email) != null;
    }
}
