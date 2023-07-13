package com.cystrix.blog.util;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Component;

/**
 * @author: chenyue7@foxmail.com
 * @date: 13/7/2023
 * @description:
 */
@Component
public class MD5Utils {

    private final String salt = "a972kjs02jf85nsdla91f,';[3";

    /**
     * 获得md5加密后的base64编码
     * @param password 密码明文
     */
    public String encryption(String password) {
        Md5Hash md5Hash = new Md5Hash(password, salt, 1024);
        return md5Hash.toBase64();
    }
}
