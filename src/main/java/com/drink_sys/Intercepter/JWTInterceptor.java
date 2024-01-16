package com.drink_sys.Intercepter;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.drink_sys.exception.ZeporeException;
import com.drink_sys.util.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;


@Slf4j
public class JWTInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ZeporeException, IOException {
        String path = request.getRequestURI();
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token)){
            response.sendRedirect("/web/user/login");
            throw new ZeporeException("token不能为空");
        }
        try {
            JWTUtils.verify(token);
        } catch (SignatureVerificationException e) {
            log.error("无效签名！ 错误 ->", e);
            return false;
        } catch (TokenExpiredException e) {
            log.error("token过期！ 错误 ->", e);
            return false;
        } catch (AlgorithmMismatchException e) {
            log.error("token算法不一致！ 错误 ->", e);
            return false;
        } catch (Exception e) {
            log.error("token无效！ 错误 ->", e);
            return false;
        }
        return true;
    }
/*@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        return !StringUtils.isEmpty(token);
    }*/

}
