package com.cystrix.blog.vo;

import lombok.Data;

/**
 * @author: chenyue7@foxmail.com
 * @date: 18/7/2023
 * @description:
 */
@Data
public class LoginVo {
    private String email;
    private String password;
    private String verificationCode;
}
