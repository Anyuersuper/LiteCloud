package com.litecloud.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Users user, HttpServletRequest request) {
        Map<String, Object> res = userService.login(user.getUsername(), user.getPassword());
        if ("success".equals(res.get("status"))) {
            request.getSession().setAttribute("userId", res.get("userId"));
            String role = (String) res.get("role");
            String springRole = "ROLE_USER";
            if ("admin".equals(role)) {
                springRole = "ROLE_ADMIN";
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user.getUsername(), null,
                    java.util.Collections.singletonList(new SimpleGrantedAuthority(springRole)));
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
}
