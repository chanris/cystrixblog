package com.cystrix.blog.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.cystrix.blog.dao.UserInfoDao;
import com.cystrix.blog.entity.UserInfo;
import com.cystrix.blog.enums.RedisEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * @author: chenyue7@foxmail.com
 * @date: 10/7/2023
 * @description:
 */
@Component
public class JwtUtils {
    @Value("${jwt.secret-key}")
    private String secretKey;
    @Resource
    // 缓存
    private RedisUtils redisUtils;
    // token过期时间,单位秒
    private static final long TOKEN_EXPIRED_TIME = 60 * 60;
    // token刷新时机
    private static final long REFRESH_THRESHOLD = TOKEN_EXPIRED_TIME / 4;

    public String createTokenByUser(UserInfo userInfo){
        String jwtId = UUID.randomUUID().toString().replaceAll("-","");
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String token = JWT.create()
                .withClaim("username", userInfo.getUsername())
                .withClaim("user-id", userInfo.getId())
                .withClaim("jwt-id", jwtId)
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRED_TIME * 1000))
                .sign(algorithm);
        String cacheName = RedisEnum.LOGIN_TOKEN_.name() + userInfo.getId();
        redisUtils.setValue(cacheName, token);
        redisUtils.setExpireTime(cacheName, TOKEN_EXPIRED_TIME);
        return token;
    }

    /**
     * 验证token
     */
    public boolean verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username",getUsernameByToken(token))
                    .withClaim("user-id",getUserIdByToken(token))
                    .withClaim("jwt-id", getJwtIdByToken(token))
                    .build();
            //3. 验证token
            verifier.verify(token);
            //4. 验证缓存
            String cacheName = RedisEnum.LOGIN_TOKEN_.name() + getUserIdByToken(token);
            String redisToken = redisUtils.getValue(cacheName);
            return token.equals(redisToken);
        } catch (Exception e) { //捕捉到任何异常都视为校验失败
            return false;
        }
    }

    /**
     * 刷新token信息
     * @param userId
     * @return
     */
    public String refreshToken(Integer userId) {
        String cacheName = RedisEnum.LOGIN_TOKEN_.name() + userId;
        if(!(redisUtils.isExistedKey(cacheName) &&
                (redisUtils.getExpireTime(cacheName) < REFRESH_THRESHOLD))) {
            return null;
        }
        String oldToken = redisUtils.getValue(cacheName);
        UserInfo userInfo = new UserInfo();
        userInfo.setId(getUserIdByToken(oldToken));
        userInfo.setUsername(getUsernameByToken(oldToken));
        return createTokenByUser(userInfo);
    }

    /**
     * 根据Token获取Username
     */
    public String getUsernameByToken(String token)  {
        return JWT.decode(token).getClaim("username").asString();
    }
    /**
     * 根据Token 获取jwt-id
     */
    public String getJwtIdByToken(String token)  {
        return JWT.decode(token).getClaim("jwt-id").asString();
    }
    /**
     * 根据Token 获取user-id
     */
    public Integer getUserIdByToken(String token)  {
        return JWT.decode(token).getClaim("user-id").asInt();
    }
}
