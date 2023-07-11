package com.cystrix.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description: 登录令牌信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginToken {
    private Integer id;
    private String username;
    private String token;
}
