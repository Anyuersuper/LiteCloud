package com.litecloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // 禁用CSRF保护
                .authorizeHttpRequests()
                .antMatchers(
                        "/", "/logout", "/login", "/login.html")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().maximumSessions(5);
        return http.build();
    }
}
