package com.cystrix.blog.conf.filter;


import com.cystrix.blog.conf.JwtShiroToken;
import com.cystrix.blog.enums.CodeEnum;
import com.cystrix.blog.vo.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
public class JwtShiroFilter extends BasicHttpAuthenticationFilter {

    private final static Logger log = LoggerFactory.getLogger(JwtShiroFilter.class);

    /**
     * 判断是否为认证请求
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        String path = httpServletRequest.getServletPath();
        log.info("request path: {}", path);
        return StringUtils.hasLength(getAuthzHeader(request));
    }

    /**
     * 认证逻辑方法
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        // 获取请求头上的token，字段名为Authorization
        String token = getAuthzHeader(request);

        getSubject(request, response).login(new JwtShiroToken(token));
        return true;
    }

    /**
     *
     * 认证调用方法
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                return executeLogin(request, response);
            }catch (Exception e) {
                return false;
            }
        }else {
            return false;
        }
    }



    /**
     * 提供跨域支持
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        log.debug("*****************preHandle**********************");
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request) ;
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        log.debug("*****************onAccessDenied**********************");
        // 未验证返回
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setHeader("Content-Type", "application/json");
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter printWriter = httpServletResponse.getWriter();
        Response responseJson = Response.failed(CodeEnum.USER_UNAUTHORIZED);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writer().writeValue(printWriter, responseJson);
        return false;
    }
}
