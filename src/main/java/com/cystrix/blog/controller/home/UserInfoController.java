package com.cystrix.blog.controller.home;

import com.cystrix.blog.enums.CodeEnum;
import com.cystrix.blog.enums.RedisEnum;
import com.cystrix.blog.exception.ParameterException;
import com.cystrix.blog.service.impl.UserServiceImpl;
import com.cystrix.blog.util.EmailUtils;
import com.cystrix.blog.util.RedisUtils;
import com.cystrix.blog.vo.LoginVo;
import com.cystrix.blog.vo.Response;
import com.cystrix.blog.view.UserInfoView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "前端用户管理")
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

    @ApiOperation(value = "获取用户信息")
    @RequestMapping(value = "")
    public Response getUserInfo() {
        UserInfoView userInfoView = userService.getUserInfoVo();
        return Response.ok(userInfoView);
    }

    @ApiOperation(value = "获取验证码")
    @RequestMapping(value = "/getVerificationCode")
    public Response getVerificationCode(@ApiParam(value = "登录信息") @RequestBody LoginVo vo) {
        try {
            Assert.notNull(vo.getEmail(), "邮箱不能为空");
            Assert.isTrue(userService.isExistedUser(vo.getEmail()), "用户不存在");
        }catch (Exception e) {
            throw new ParameterException(e.getMessage());
        }
        // 一分钟内最多30个请求
        boolean allowed = redisUtils.isActionAllowed(RedisEnum.VC_LIMIT_RATE.toString(), "send_code", 60, 30);
        if (!allowed) return Response.failed(CodeEnum.INTER_SERVER_UNAVAILABLE);
        emailUtils.sendVerificationCodeToEmail(vo.getEmail());
        return Response.ok();
    }
}