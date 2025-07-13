package com.litecloud.service;

import org.springframework.web.multipart.MultipartFile;

import com.litecloud.entity.Files;
import java.util.Map;

public interface FileService {
    Map<String, Object> upload(MultipartFile file, Long parentId, Long ownerId);

    Map<String, Object> mkdir(Files file);

    Map<String, Object> deldir(Files file);

    Map<String, Object> list(Long parentId, Long ownerId);

    /**
     * 文件流下载，返回字节数组和文件名
     */
    Map<String, Object> downloadFile(Long id, String userAgent) throws Exception;
}
