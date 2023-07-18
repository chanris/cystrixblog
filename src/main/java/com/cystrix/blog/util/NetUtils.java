package com.cystrix.blog.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: chenyue7@foxmail.com
 * @date: 18/7/2023
 * @description:
 */
@Component
public class NetUtils {

    /**
     * 获得请求ip地址
     * @param request
     * @return
     */
    public String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forward-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unkown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}
