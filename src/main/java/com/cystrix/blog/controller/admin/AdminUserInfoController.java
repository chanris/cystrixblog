package com.cystrix.blog.controller.admin;

import com.cystrix.blog.entity.UserInfo;
import com.cystrix.blog.enums.CodeEnum;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.service.impl.UserServiceImpl;
import com.cystrix.blog.vo.LoginToken;
import com.cystrix.blog.vo.Response;
import org.apache.shiro.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
@RestController
@RequestMapping(value = "/admin/user")
public class AdminUserInfoController {

    private final UserServiceImpl userService;

    public AdminUserInfoController(UserServiceImpl userService) {
        this.userService = userService;
    }


    // TODO 7/11 邮箱验证码功能还没实现
    @RequestMapping(value = "/login")
    public Response login(@RequestBody UserInfo userInfo) {
        try {
            Assert.notNull(userInfo.getUsername(), "用户名不可为空");
            Assert.notNull(userInfo.getPassword(), "密码不可为空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        LoginToken tokenVo = userService.doLoginHandle(userInfo);
        return Response.builder().code(CodeEnum.OK.code).msg("登录成功").data(tokenVo).build();
    }

    /**
     * 添加用户
     */
    @PostMapping(value = "/add")
    public Response createUser(@RequestBody UserInfo userInfo) {
        try {
            Assert.notNull(userInfo.getUsername(), "用户名不能为空");
            Assert.notNull(userInfo.getPassword(), "密码不能为空");
            Assert.notNull(userInfo.getEmail(), "邮箱不能为空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        if (checkUserInfoFormat(userInfo)) {
            throw new ParameterException("用户信息格式错误");
        }
        userService.addUserInfo(userInfo);
        return Response.ok();
    }

    @PostMapping (value = "/update")
    public Response updateUserInfo(@RequestBody UserInfo userInfo) {
        try {
            Assert.notNull(userInfo.getId(), "id不能为空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        return Response.ok();
    }

    private boolean checkUserInfoFormat(UserInfo userInfo) {
        String regexUsername = "[\\d|\\w]{6,20}";
        String regexPasswd = "\\w[\\d|\\w]{5,19}";
        String regexEmail = "(?=^.{3,256}$)^([\\w\\-]+([\\.][\\w\\-]+)*)@[a-zA-Z0-9][a-zA-Z0-9\\-]*[\\.][A-Za-z]{2,6}$\n";
        if (Pattern.matches(regexUsername, userInfo.getUsername())) {
            return false;
        }else if (Pattern.matches(regexPasswd, userInfo.getPassword())) {
            return false;
        }else return !Pattern.matches(regexEmail, userInfo.getEmail());
    }

}
