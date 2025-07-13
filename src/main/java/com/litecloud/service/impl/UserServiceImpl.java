package com.litecloud.service.impl;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.litecloud.entity.Files;
import com.litecloud.entity.Users;
import com.litecloud.mapper.FilesMapper;
import com.litecloud.mapper.UsersMapper;
import com.litecloud.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper userMapper;

    @Autowired
    private FilesMapper filesMapper;

    @Value("${litecloud.file.base-path}")
    private String basePath;

    @Override
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> result = new HashMap<>();
        Users user = userMapper.login(username, password);

        if (user != null) {
            Long userId = user.getId();
            String userDirPath = basePath + "/u" + userId;
            File userDir = new File(userDirPath);
            if (!userDir.exists()) {
                boolean created = userDir.mkdirs();
                if (!created) {
                    result.put("status", "error");
                    result.put("message", "用户磁盘目录创建失败！");
                    return result;
                }
            }
            Files rootDir = filesMapper.selectRootByUserId(userId);
            if (rootDir == null) {
                Files root = new Files();
                root.setFileName("根目录");
                root.setFileType("dir");
                root.setSize(0L);
                root.setPath(null);
                root.setIsDir(1);
                root.setParentId(null);
                root.setOwnerId(userId);
                root.setCreatedAt(new Date());
                filesMapper.insert(root);
                rootDir = filesMapper.selectRootByUserId(userId);
            }
            result.put("status", "success");
            result.put("message", "Login successful");
            result.put("userId", userId);
            result.put("rootFileId", rootDir.getId());
        } else {
            result.put("status", "error");
            result.put("message", "Invalid username or password");
        }
        return result;
    }

    @Override
    public Map<String, Object> adminUpdate(Users user) {
        Map<String, Object> res = new HashMap<>();
        int result = userMapper.updateByPrimaryKey(user);
        if (result > 0) {
            res.put("status", "success");
            res.put("message", "User updated successfully");
        } else {
            res.put("status", "fail");
            res.put("message", "Failed to update user");
        }
        return res;
    }

    @Override
    public Map<String, Object> addUser(Users user) {
        Map<String, Object> res = new HashMap<>();
        int result = userMapper.insert(user);
        if (result > 0) {
            res.put("status", "success");
            res.put("message", "User added successfully");
        } else {
            res.put("status", "fail");
            res.put("message", "Failed to add user");
        }
        return res;
    }

    @Override
    public Map<String, Object> listUsers(int page, int size) {
        Map<String, Object> res = new HashMap<>();
        int offset = (page - 1) * size;
        List<Users> users = userMapper.listUsers(offset, size);
        if (users != null && !users.isEmpty()) {
            res.put("status", "success");
            res.put("data", users);
        } else {
            res.put("status", "fail");
            res.put("message", "No users found");
        }
        return res;
    }
}
