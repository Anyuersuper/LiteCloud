package com.litecloud.service.impl;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
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
}
