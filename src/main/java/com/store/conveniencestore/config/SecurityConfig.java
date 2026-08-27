package com.store.conveniencestore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                // 当前是前后端分离的JWT接口，暂时关闭CSRF
                .csrf(AbstractHttpConfigurer::disable)

                // 不使用Spring Security默认登录页面
                .formLogin(AbstractHttpConfigurer::disable)

                // 不使用HTTP Basic登录
                .httpBasic(AbstractHttpConfigurer::disable)

                // JWT不依赖服务器Session
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // 当前先放行全部接口，JWT完成后再收紧
                .authorizeHttpRequests(authorize ->
                        authorize.anyRequest().permitAll()
                );

        return http.build();
    }


    /**
     * 用于加密和校验用户密码。
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 注册时把密码变成哈希值存入数据库；登录时用 matches() 检查用户输入和哈希值是否对应。
        return new BCryptPasswordEncoder();
    }

}
