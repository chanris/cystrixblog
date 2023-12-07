package com.cystrix.blog.controller.admin;

import com.cystrix.blog.entity.UserInfo;
import com.cystrix.blog.enums.CodeEnum;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.query.UserQuery;
import com.cystrix.blog.service.impl.UserServiceImpl;
import com.cystrix.blog.vo.LoginToken;
import com.cystrix.blog.vo.LoginVo;
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

    @RequestMapping(value = "/login")
    public Response login(@RequestBody LoginVo vo) {
        try {
            Assert.notNull(vo.getEmail(), "邮箱不可为空");
            Assert.notNull(vo.getPassword(), "密码不可为空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        LoginToken tokenVo = userService.doLoginHandle(vo);
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
        if (!checkUserInfoFormat(userInfo)) {
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
        userService.modifyUserInfo(userInfo);
        return Response.ok();
    }

    @PostMapping(value = "/delete")
    public Response deleteUserInfo(@RequestBody UserQuery userQuery) {
        try {
            Assert.notNull(userQuery.getId(), "id不能为空");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        userService.removeUserInfo(userQuery.getId());
        return Response.ok();
    }

    private boolean checkUserInfoFormat(UserInfo userInfo) {
        String regexUsername = "\\w{6,20}";
        String username = userInfo.getUsername();
        String passwd = userInfo.getPassword();
        String email = userInfo.getEmail();
        if (!checkPasswdAndEmailFormat(passwd, email)) {
            return false;
        }
        return Pattern.matches(regexUsername, username);
    }

    private boolean checkPasswdAndEmailFormat(String passwd, String email) {
        String regexPasswd = "\\w\\w{5,19}";
        String regexEmail = "(?=^.{3,256}$)^([\\w\\-]+([\\.][\\w\\-]+)*)@[a-zA-Z0-9][a-zA-Z0-9\\-]*[\\.][A-Za-z]{2,6}$\n";
        if (!Pattern.matches(regexPasswd, passwd)) {
            return false;
        }else return Pattern.matches(regexEmail, email);
    }

}
