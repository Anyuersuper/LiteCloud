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
                .csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/logout", "/", "/login.html", "/login").permitAll()
                .antMatchers("/admin", "admin.html").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .sessionManagement().maximumSessions(5);
        return http.build();
    }
}
