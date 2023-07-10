package com.cystrix.blog.conf;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
public class JwtShiroToken implements AuthenticationToken {
    private String token;

    public JwtShiroToken(String token) {
        this.token = token;
    }
    @Override
    public Object getPrincipal() {
        return this.token;
    }
    @Override
    public Object getCredentials() {
        return this.token;
    }
}
