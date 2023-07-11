package com.cystrix.blog.controller.admin;

import com.cystrix.blog.entity.UserInfo;
import com.cystrix.blog.enums.CodeEnum;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.service.impl.UserServiceImpl;
import com.cystrix.blog.vo.LoginToken;
import com.cystrix.blog.vo.Response;
import org.apache.shiro.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

}
