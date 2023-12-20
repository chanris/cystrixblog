package com.cystrix.blog.conf;


import com.cystrix.blog.conf.filter.JwtShiroFilter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
@Configuration
public class ShiroConf {


    /**
     * 相同的url规则，后面定义的会覆盖前面的
     * 两个url规则都可以匹配同一个url，只执行第一个
     *
     * shiroFilter
     * shiro自带的过滤器
     * 过滤器名称都在 DefaultFilter
     * anon  AnonymousFilter
     * authc FormAuthenticationFilter
     * authBasic
     * logout LogoutFilter
     * noSessionCreation
     * perms
     * port
     * rest
     * roles
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean filterFactory = new ShiroFilterFactoryBean();
        filterFactory.setSecurityManager(securityManager);
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("token", new JwtShiroFilter());
        filterFactory.setFilters(filterMap);
        Map<String, String> filterRuleMap = new LinkedHashMap<>();
        // 相同的url匹配规则, 后面的会覆盖前面的是因为map数据结构的原理
        //map.put("/user/**", "authc");
        filterRuleMap.put("/home/**", "anon");
        filterRuleMap.put("/admin/user/login", "anon");
        filterRuleMap.put("/admin/**", "token");
        filterFactory.setFilterChainDefinitionMap(filterRuleMap);
        return filterFactory;
    }

    // securityManager
    // 注意: 在web环境下,配置的是DefaultWebSecurityManager
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(Realm realm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);

        //关闭session
        DefaultSubjectDAO subjectDAO = (DefaultSubjectDAO) securityManager.getSubjectDAO();
        DefaultSessionStorageEvaluator evaluator = (DefaultSessionStorageEvaluator) subjectDAO.getSessionStorageEvaluator();
        evaluator.setSessionStorageEnabled(Boolean.FALSE);
        subjectDAO.setSessionStorageEvaluator(evaluator);
        return securityManager;
    }

    // spring boot 解决跨域问题
    @Bean
    public WebMvcConfigurer crosConfigurer(){
        System.out.println("cros");
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                //服务器的哪个rul支持跨域
                registry.addMapping("/**").allowedMethods("*")
                        .allowedHeaders("*")
                        .allowedOrigins("*");
            }
        };
    }

}
