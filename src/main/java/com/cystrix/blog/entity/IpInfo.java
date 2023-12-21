package com.cystrix.blog.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenyue7@foxmail.com
 * @date 21/12/2023
 * @description
 */
@Getter
@Setter
public class IpInfo {
    // siteHistory id
    private Long id;
    private String ip;
    private String city;
    private String region;
    private String country;
    private String loc;
    private String org;
    private String timezone;
}
