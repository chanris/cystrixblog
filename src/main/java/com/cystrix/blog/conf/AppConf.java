package com.cystrix.blog.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author chenyue7@foxmail.com
 * @date 21/12/2023
 * @description
 */
@Configuration
public class AppConf {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
