package com.litecloud.service;

import java.util.Map;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

import com.litecloud.entity.Users;

@Service
public interface UserService {
    Map<String, Object> login(String username, String password);

    Map<String, Object> adminUpdate(Users user);

    Map<String, Object> addUser(Users user);

    Map<String, Object> listUsers(int page, int size);
}
