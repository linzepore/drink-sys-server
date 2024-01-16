package com.drink_sys.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.drink_sys.entity.User;
import com.drink_sys.exception.ZeporeException;
import com.drink_sys.mapper.UserMapper;
import com.drink_sys.service.server.WUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class JWTUtils {

    private static WUserService wUserService;

    @Autowired
    public void setWUserService(WUserService wUserService) {
        JWTUtils.wUserService = wUserService;
    }

    public static String getToken(User u) {
        Calendar instance = Calendar.getInstance();
        //默认令牌过期时间7天
        instance.add(Calendar.DATE, 7);

        JWTCreator.Builder builder = JWT.create();
        builder.withClaim("userId", u.getUid())
                .withClaim("username", u.getName());

        return builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(u.getOpenId()));
    }

    /**
     * 验证token合法性 成功返回token
     */
    public static DecodedJWT verify(String token) {
        if(StringUtils.isEmpty(token)){
            // 如果token为空，返回null
            return null;
        }

        String name = JWT.decode(token).getClaim("username").asString();
        User user = wUserService.getUserByName(name);
        if (user == null) {
            // 如果找不到对应的用户，返回null
            return null;
        }

        String password = user.getOpenId();
        JWTVerifier build = JWT.require(Algorithm.HMAC256(password)).build();

        // 验证成功返回DecodedJWT，验证失败返回null
        try {
            return build.verify(token);
        } catch (Exception e) {
            return null;
        }
    }
}
