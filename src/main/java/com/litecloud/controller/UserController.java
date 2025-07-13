package com.litecloud.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.litecloud.entity.Users;
import com.litecloud.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Users user, HttpServletRequest request) {
        Map<String, Object> res = userService.login(user.getUsername(), user.getPassword());
        if ("success".equals(res.get("status"))) {
            // 登录成功后写入Session
            request.getSession().setAttribute("userId", res.get("userId"));
            // 设置Spring Security认证
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user.getUsername(), null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        return res;
    }
}
