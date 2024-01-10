package com.cystrix.blog.util;

import com.cystrix.blog.dao.SiteHistoryDao;
import com.cystrix.blog.entity.SiteHistory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: chenyue7@foxmail.com
 * @date: 18/7/2023
 * @description:
 */
@Slf4j
@Component
public class NetUtils {
    @Resource
    private RestTemplate restTemplate;

    @Resource
    private SiteHistoryDao siteHistoryDao;

    @Value("${ipinfo.token}")
    private String token;

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

        // 如果通过代理服务器获取到多个IP，那么取第一个非unknown的IP为真实IP
        if (ipAddress != null && ipAddress.contains(",")) {
            ipAddress = ipAddress.split(",")[0].trim();
        }

        return ipAddress;
    }

    /**
     * 获得ip属地
     */
    public void setIPInfo(SiteHistory history) {
        String url = "https://ipinfo.io/" + history.getRealIP() + "?token=" + token;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            String result = response.getBody();
            ObjectMapper om = new ObjectMapper();
            try {
                IPInfo ipInfo = om.readValue(result, IPInfo.class);
                history.setCity(ipInfo.getCity());
                history.setCountry(ipInfo.getCountry());
                history.setRealIP(ipInfo.getIp());
                history.setLoc(ipInfo.getLoc());
                history.setOrg(ipInfo.getOrg());
                history.setTimezone(ipInfo.getTimezone());
                history.setRegion(ipInfo.getRegion());
                siteHistoryDao.update(history);
            } catch (JsonProcessingException e) {
                log.warn("ip info address exception, json format error {}", result);
            }
        }else {
            log.warn("ip info address failed, response code: {}", response.getStatusCodeValue());
        }
    }

    private static class IPInfo {
        String ip;
        String city;
        String region;
        String country;
        String loc;
        String org;
        String timezone;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getLoc() {
            return loc;
        }

        public void setLoc(String loc) {
            this.loc = loc;
        }

        public String getOrg() {
            return org;
        }

        public void setOrg(String org) {
            this.org = org;
        }

        public String getTimezone() {
            return timezone;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }
    }

}
