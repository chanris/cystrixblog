package com.cystrix.blog.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
@Data
public class UserInfo {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private String motto;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer isDelete;
}
