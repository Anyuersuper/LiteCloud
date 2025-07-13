package com.litecloud.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Map<String, Object> login(String username, String password);
}
