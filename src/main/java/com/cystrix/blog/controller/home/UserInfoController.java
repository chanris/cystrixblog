package com.cystrix.blog.controller.home;

import com.cystrix.blog.service.impl.UserServiceImpl;
import com.cystrix.blog.vo.Response;
import com.cystrix.blog.vo.UserInfoVo;
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

    public UserInfoController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/userInfo")
    public Response getUserInfo() {
        UserInfoVo userInfoVo = userService.getUserInfoVo(1);
        return Response.ok(userInfoVo);
    }
}
