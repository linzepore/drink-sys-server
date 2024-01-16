package com.drink_sys.controller.server;

import com.drink_sys.entity.Msg;
import com.drink_sys.entity.User;
import com.drink_sys.service.client.UserService;
import com.drink_sys.service.server.WUserService;
import com.drink_sys.util.JWTUtils;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Tag(name = "B端用户管理")
@RequestMapping("/web/user")
public class UserServiceController {
        @Autowired
        WUserService wUserService;
        @GetMapping("/login")
        public Msg<String> login(String username, String password) {
            Msg<String> stringMsg = new Msg<>();
            // 获取当前Subject
            Subject subject = SecurityUtils.getSubject();
            // 封装用户的登录数据
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            try {
                subject.login(token);
            } catch (UnknownAccountException u) {
                return stringMsg.beFailed(null, "用户名错误");
            } catch (IncorrectCredentialsException i) {
                return stringMsg.beFailed(null, "密码错误");
            } catch (Exception e) {
                return stringMsg.beFailed(null, "未知错误");
            }
            User user = new User();
            user.setOpenId(password);
            user.setName(username);
            return new Msg<String>().beSucceed(JWTUtils.getToken(user), "验证成功");
        }

    /*
    @GetMapping("/login")
    public Msg<String> login(String username, String password) {
        Msg<String> stringMsg = new Msg<>();

        // 获取当前Subject
        Subject subject = SecurityUtils.getSubject();

        // 封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        try {
            // 提交登录
            subject.login(token);

            // 登录成功，生成Token
            User u = new User();
            u.setOpenId(password);
            u.setName(username);
            String jwtToken = JWTUtils.getToken(u);

            return stringMsg.beSucceed(jwtToken, "Token获取成功");
        } catch (AuthenticationException e) {
            // 登录失败，处理异常并重定向到登录页
            return stringMsg.beFailed(null, "验证失败");
        }
    }*/
}