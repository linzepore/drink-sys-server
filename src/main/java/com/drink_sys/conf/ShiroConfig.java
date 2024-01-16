package com.drink_sys.conf;

import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Bean(name = "shiroRealm")
    public ShiroRealm shiroRealm() {
        return new ShiroRealm();
    }
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(ShiroRealm shiroRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm);
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);

        // 添加 shiro 的内置过滤器和配置过滤规则
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/web/user/login", "anon"); // 允许匿名访问登录接口
//        filterMap.put("/web/user/**", "anon"); // 允许匿名访问其他登录相关接口
        filterMap.put("/**", "authc"); // 其他接口需要认证

        bean.setFilterChainDefinitionMap(filterMap);
        bean.setLoginUrl("/web/user/login"); // 登录失败时重定向到登录页

        // 设置登录成功后的跳转页面，如果不设置默认跳转到 "successUrl"
        bean.setSuccessUrl("/web/success");

        // 设置未授权时跳转的页面
        bean.setUnauthorizedUrl("/web/unauthorized");


        return bean;
    }
}
