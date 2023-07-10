package com.cystrix.blog.conf;

import com.cystrix.blog.service.impl.UserServiceImpl;
import com.cystrix.blog.util.JwtUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
@Component
public class UserRealm extends AuthorizingRealm {

    private static Logger log = LoggerFactory.getLogger(UserRealm.class);
    private JwtUtils jwtUtils;
    private UserServiceImpl userService;
    public UserRealm(JwtUtils jwtUtils, UserServiceImpl userService) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    /**
     * 该方法是为了判断这个主体能否被本Realm处理，
     * 判断的方法是查看token是否为同一个类型
     * 指定本realm能处理的AuthenticationToken
     * @param authenticationToken
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        return authenticationToken instanceof JwtShiroToken;
    }

    // 授权（返回用户拥有的权限列表）
    // 本系统只有一个管理员，所以不需要授权管理
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    // 认证（登录）
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String receivedToken = authenticationToken.getPrincipal().toString();
        Integer userId;
        try {
            userId = jwtUtils.getUserIdByToken(receivedToken);
        }catch (Exception e) {
            throw new AuthenticationException("token解析错误");
        }
        if (!jwtUtils.verifyToken(receivedToken)) {
            throw new AuthenticationException("token验证错误");
        }
        return new SimpleAuthenticationInfo(userId, receivedToken, getName());
    }

    /**
     * 注意坑点 : 密码校验 , 这里因为是JWT形式,就无需密码校验和加密,直接让其返回为true(如果不设置的话,该值默认为false,即始终验证不通过)
     */
    @Override
    public CredentialsMatcher getCredentialsMatcher() {
        return (token, info) -> true;
    }
}
