package com.litecloud.controller;

import com.litecloud.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.litecloud.entity.Files;
import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 文件流下载接口
     * 访问示例: /files/downloadFile?id=3
     */
    @GetMapping("/downloadFile")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("id") Long id, HttpServletRequest request)
            throws Exception {
        String userAgent = request.getHeader("User-Agent");
        Map<String, Object> result = fileService.downloadFile(id, userAgent);
        if (!"success".equals(result.get("status"))) {
            return ResponseEntity.status(404).body(null);
        }
        String encodedFileName = (String) result.get("fileName");
        byte[] fileBytes = (byte[]) result.get("fileBytes");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileBytes);
    }

    /**
     * 文件上传只能用form-data
     */
    @PostMapping("/upload")
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file,
            @RequestParam("parentId") Long parentId,
            @RequestParam("ownerId") Long ownerId) {
        return fileService.upload(file, parentId, ownerId);
    }

    /**
     * 创建目录
     * 示例 JSON 如下：
     *
     * {
     * "fileName": "example", // 新目录的名称
     * "parentId": 4, // 父目录 ID（即该目录将创建在其下）
     * "ownerId": 3 // 所属用户的 ID
     * }
     */
    @PostMapping("/mkdir")
    public Map<String, Object> mkdir(@RequestBody Files file) {
        return fileService.mkdir(file);
    }

    /**
     * 删除文件或者目录
     * 示例 JSON 如下：
     * 
     * {
     * "id": 3, // 要删除的主键id
     * }
     */
    @DeleteMapping("/deldir")
    public Map<String, Object> deldir(@RequestBody Files file) {
        return fileService.deldir(file);
    }

    @GetMapping("/list")
    public Map<String, Object> list(@RequestParam("parentId") Long parentId,
            @RequestParam("ownerId") Long ownerId) {
        return fileService.list(parentId, ownerId);
    }

}
