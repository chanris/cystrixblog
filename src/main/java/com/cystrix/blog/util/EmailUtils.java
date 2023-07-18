package com.cystrix.blog.util;

import com.cystrix.blog.exception.EmailOpsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;
import java.util.Random;

/**
 * @author: chenyue7@foxmail.com
 * @date: 18/7/2023
 * @description:
 */
@Component
public class EmailUtils {

    private JavaMailSender javaMailSender;
    private RedisUtils redisUtils;

    public EmailUtils(JavaMailSender javaMailSender, RedisUtils redisUtils) {
        this.javaMailSender = javaMailSender;
        this.redisUtils = redisUtils;
    }

    public void sendVerificationCodeToEmail(String emailReceiver) {
        SimpleMailMessage message = new SimpleMailMessage();
        String verificationCode = generateEmailVerificationCode(5);
        message.setSubject("CystrixBlog后台登录验证码");
        message.setText("尊敬的用户您好!\n\n欢迎使用CystrixBlog系统。\n\n您的登录验证码为："+ verificationCode +
                ",有效期5分钟，请不要把验证码信息泄露给其他人，如非本人请勿操作。");
        message.setTo(emailReceiver);
        try {
            message.setFrom(new InternetAddress(MimeUtility.encodeText("CystrixBlog系统服务") + "<chenyue7@qq.com>").toString());
            javaMailSender.send(message);
            String redisKey = "LOGIN_CODE_" + emailReceiver;
            redisUtils.setValue(redisKey, verificationCode);
            redisUtils.setExpireTime(redisKey, 60 * 5);
        }catch (Exception e) {
            throw new EmailOpsException(e.getMessage());
        }
    }


    public String generateEmailVerificationCode(int length) {
        Random random = new Random(System.currentTimeMillis());
        StringBuilder verificationCode = new StringBuilder();
        String c = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < length; i++) {
            // 从字符集中随机选择一个字符，并将其添加到验证码中
            int index = random.nextInt(c.length());
            char randomChar = c.charAt(index);
            verificationCode.append(randomChar);
        }
        return verificationCode.toString();
    }


}
