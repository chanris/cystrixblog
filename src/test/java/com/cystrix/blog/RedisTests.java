package com.cystrix.blog;

import com.cystrix.blog.util.EmailUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author: chenyue7@foxmail.com
 * @date: 18/7/2023
 * @description:
 */
@SpringBootTest
public class RedisTests {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private EmailUtils emailUtils;

    @Test
    public void test01() {
     //   assert  redisTemplate != null;
        System.out.println(redisTemplate.delete("username"));;
    }

    @Test
    public void sendEmailTest() {
        String emailReceiver = "2548519559@qq.com";
        emailUtils.sendVerificationCodeToEmail(emailReceiver);
    }
}
