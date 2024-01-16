package com.drink_sys.conf;

import com.drink_sys.Intercepter.JWTInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("InterceptorConfig addInterceptors called");
        registry.addInterceptor(new JWTInterceptor())
                //拦截的路径
                .addPathPatterns("/**")
                //排除登录接口
                .excludePathPatterns("/web/user/login");
    }
}
