package com.cystrix.blog.controller.home;

import com.cystrix.blog.entity.UserInfo;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.service.impl.UserServiceImpl;
import com.cystrix.blog.util.EmailUtils;
import com.cystrix.blog.vo.LoginVo;
import com.cystrix.blog.vo.Response;
import com.cystrix.blog.vo.UserInfoVo;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: chenyue7@foxmail.com
 * @date: 11/7/2023
 * @description:
 */
@RestController
@RequestMapping(value = "/home/user")
public class UserInfoController {

    private final UserServiceImpl userService;
    private EmailUtils emailUtils;

    public UserInfoController(UserServiceImpl userService, EmailUtils emailUtils) {
        this.userService = userService;
        this.emailUtils = emailUtils;
    }

    @RequestMapping(value = "/userInfo")
    public Response getUserInfo() {
        UserInfoVo userInfoVo = userService.getUserInfoVo(1);
        return Response.ok(userInfoVo);
    }

    @RequestMapping(value = "/getVerificationCode")
    public Response getVerificationCode(@RequestBody LoginVo vo) {
        try {
            Assert.notNull(vo.getEmail(), "邮箱不能为空");
            Assert.isTrue(userService.isExistedUser(vo.getEmail()), "用户不存在");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        emailUtils.sendVerificationCodeToEmail(vo.getEmail());
        return Response.ok();
    }

    @GetMapping(value = "/test")
    public  Response test() {
        return Response.ok();
    }
}
