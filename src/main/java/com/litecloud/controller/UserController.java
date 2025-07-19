package com.litecloud.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.litecloud.entity.Users;
import com.litecloud.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/check-login")
    public Map<String, Object> checkLogin(HttpServletRequest request) {
        Object userId = request.getSession().getAttribute("userId");
        if (userId != null) {
            return Map.of("status", "success", "userId", userId);
        }
        return Map.of("status", "error", "message", "未登录");
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Users user, HttpServletRequest request) {
        Map<String, Object> res = userService.login(user.getUsername(), user.getPassword());
        if ("success".equals(res.get("status"))) {
            // 登录成功后强制创建Session
            request.getSession();
            request.getSession().setAttribute("userId", res.get("userId"));
            // 设置Spring Security认证
            String role = (String) res.get("role");
            String springRole = "ROLE_USER";
            if ("admin".equals(role)) {
                springRole = "ROLE_ADMIN";
            }
            org.springframework.security.core.GrantedAuthority authority = new org.springframework.security.core.authority.SimpleGrantedAuthority(
                    springRole);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user.getUsername(), null, Collections.singletonList(authority));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        return res;
    }

    @PostMapping("/admin/update")
    public Map<String, Object> admin(@RequestBody Users user) {
        return userService.adminUpdate(user);
    }

    @PostMapping("/admin/add")
    public Map<String, Object> addUser(@RequestBody Users user) {
        return userService.addUser(user);
    }

    @GetMapping("/admin/list")
    public Map<String, Object> listUsers(@RequestParam int page,
            @RequestParam int size) {
        return userService.listUsers(page, size);
    }

    @DeleteMapping("/admin/delete")
    public Map<String, Object> deleteUser(@RequestParam Long userId) {
        return userService.deleteUser(userId);
    }

}
