package com.cystrix.blog.view;

import lombok.Data;

/**
 * @author: chenyue7@foxmail.com
 * @date: 11/7/2023
 * @description:
 */
@Data
public class UserInfoView {
    private String nickname;
    private String avatar;
    private String email;
    private String motto;
    private Integer articleNum;
    private Integer tagNum;
    private Integer categoryNum;
}
