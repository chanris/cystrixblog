package com.cystrix.blog.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.cystrix.blog.entity.UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
    @Value("${jwt.cache-name}")
    private String cacheName;
    // token过期时间,单位秒
    private static final long TOKEN_EXPIRED_TIME = 60 * 60;

    public String createTokenByUser(UserInfo userInfo){
        String jwtId = UUID.randomUUID().toString().replaceAll("-","");
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String token = JWT.create()
                .withClaim("username", userInfo.getUsername())
                .withClaim("user-id", userInfo.getId())
                .withClaim("jwt-id", jwtId)
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRED_TIME * 1000))
                .sign(algorithm);
        return token;
    }

    public boolean verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username",getUsernameByToken(token))
                    .withClaim("user-id",getUserIdByToken(token))
                    .withClaim("jwt-id", getJwtIdByToken(token))
                    .build();
            //3 . 加密token
            verifier.verify(token);
            return true;
        } catch (Exception e) { //捕捉到任何异常都视为校验失败
            return false;
        }
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
