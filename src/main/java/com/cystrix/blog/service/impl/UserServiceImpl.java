package com.cystrix.blog.service.impl;

import com.cystrix.blog.dao.UserInfoDao;
import com.cystrix.blog.entity.UserInfo;
import com.cystrix.blog.enums.RedisEnum;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.util.JwtUtils;
import com.cystrix.blog.util.MD5Utils;
import com.cystrix.blog.util.RedisUtils;
import com.cystrix.blog.vo.LoginToken;
import com.cystrix.blog.vo.LoginVo;
import com.cystrix.blog.view.UserInfoView;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
@Service
public class UserServiceImpl {

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


    public LoginToken doLoginHandle(LoginVo loginVo) {
        try {
            Assert.notNull(loginVo.getEmail(), "邮箱不能为空");
            String email = loginVo.getEmail();
            if(!this.isExistedUser(email)) {
                throw new ParameterException("用户不存在");
            }
            String redisKey = RedisEnum.VERIFICATION_LOGIN_PREFIX_.name() + '_' + email;
            if (loginVo.getPassword() != null) {
                String origin = loginVo.getPassword();
                String encryption = md5Utils.encryption(origin);
                UserInfo userInfo = new UserInfo();
                userInfo.setPassword(encryption);
                userInfo.setEmail(email);
                UserInfo result = userInfoDao.selectUserInfoByEmailAndPassword(userInfo);
                if (result == null) {
                    throw new ParameterException("密码错误");
                }
                return new LoginToken(result.getId(), result.getUsername(), jwtUtils.createTokenByUser(result));
            } else if(loginVo.getVerificationCode() != null) {
                UserInfo result = userInfoDao.selectUserInfoByEmail(email);
                // 验证邮箱验证码
                if (!(redisUtils.isExistedKey(redisKey) &&
                        redisUtils.getValue(redisKey).equals(loginVo.getVerificationCode()))) {
                    throw new ParameterException("邮箱验证码错误");
                }
                // 登录成功，清除 verification code 的缓存
                redisUtils.deleteKey(redisKey);
                return new LoginToken(result.getId(), result.getUsername(), jwtUtils.createTokenByUser(result));
            }else {
                throw new ParameterException("账号密码和邮箱验证码不能同时为空");
            }
//            UserInfo result = userInfoDao.selectUserInfoByEmail(loginVo.getEmail());
//            return new LoginToken(result.getId(), result.getUsername(), jwtUtils.createTokenByUser(result));
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
    }

    public UserInfoView getUserInfoVo() {
        return userInfoDao.selectUserInfoView();
    }

    public void modifyUserInfo(UserInfo userInfo) {
        String origin = userInfo.getPassword();
        if (origin != null) {
           userInfo.setPassword(md5Utils.encryption(origin));
        }
        userInfoDao.update(userInfo);
    }

    public void addUserInfo(UserInfo userInfo) {
        // 加密密码
        String encryptionPasswd = md5Utils.encryption(userInfo.getPassword());
        userInfo.setPassword(encryptionPasswd);
        userInfo.setNickname(userInfo.getUsername());
        userInfo.setCreateTime(LocalDateTime.now());
        userInfo.setUpdateTime(LocalDateTime.now());
        userInfoDao.insert(userInfo);
    }

    public boolean isExistedUser(String email) {
        return userInfoDao.selectUserInfoByEmail(email) != null;
    }
}
