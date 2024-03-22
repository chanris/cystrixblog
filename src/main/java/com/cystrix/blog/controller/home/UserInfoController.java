package com.cystrix.blog.controller.home;

import com.cystrix.blog.enums.RedisEnum;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.service.impl.UserServiceImpl;
import com.cystrix.blog.util.EmailUtils;
import com.cystrix.blog.util.RedisUtils;
import com.cystrix.blog.vo.LoginVo;
import com.cystrix.blog.vo.Response;
import com.cystrix.blog.view.UserInfoView;
import org.springframework.beans.factory.annotation.Value;
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
    private final EmailUtils emailUtils;
    private final RedisUtils redisUtils;

    @Value("${ipinfo.token}")
    private String ipinfoToken;

    public UserInfoController(UserServiceImpl userService, EmailUtils emailUtils, RedisUtils redisUtils) {
        this.userService = userService;
        this.emailUtils = emailUtils;
        this.redisUtils = redisUtils;
    }

    @RequestMapping(value = "")
    public Response getUserInfo() {
        UserInfoView userInfoView = userService.getUserInfoVo();
        return Response.ok(userInfoView);
    }

    @RequestMapping(value = "/getVerificationCode")
    public Response getVerificationCode(@RequestBody LoginVo vo) {
        redisUtils.isActionAllowed(RedisEnum.VC_LIMIT_RATE.toString(), "send_code", 60, 40);
        try {
            Assert.notNull(vo.getEmail(), "邮箱不能为空");
            Assert.isTrue(userService.isExistedUser(vo.getEmail()), "用户不存在");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        emailUtils.sendVerificationCodeToEmail(vo.getEmail());
        return Response.ok();
    }

    @GetMapping("/getIpInfoToken")
    public String getIpinfoToken() {
        return ipinfoToken;
    }
}
