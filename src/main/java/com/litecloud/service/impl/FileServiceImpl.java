
package com.litecloud.service.impl;

import java.io.FileInputStream;

import com.litecloud.entity.Files;
import com.litecloud.mapper.FilesMapper;
import com.litecloud.sdk.FileManager;
import com.litecloud.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.litecloud.entity.Files;
import java.io.File;
import java.util.*;

@Service
public class FileServiceImpl implements FileService {

    @Value("${litecloud.file.base-path}")
    private String basePath;

    @Autowired
    private FilesMapper filesMapper;

    @Override
    public Map<String, Object> upload(MultipartFile file, Long parentId, Long ownerId) {
        Map<String, Object> res = new HashMap<>();
        if (file.isEmpty()) {
            res.put("status", "fail");
            res.put("message", "文件为空");
            return res;
        }
        try {
            if (parentId != null) {
                Files parent = filesMapper.selectByPrimaryKey(parentId);
                if (parent == null) {
                    res.put("status", "fail");
                    res.put("message", "上传失败：指定的父目录不存在");
                    return res;
                }
                if (parent.getIsDir() != 1) {
                    res.put("status", "fail");
                    res.put("message", "上传失败：父ID不是一个目录");
                    return res;
                }
            }

            // 获取原始文件名和后缀
            String originalName = file.getOriginalFilename();
            String fileType = originalName.substring(originalName.lastIndexOf('.') + 1);
            long size = file.getSize();

            // 构建保存路径
            String uuidFileName = UUID.randomUUID().toString() + "." + fileType;
            String userDir = basePath + "/u" + ownerId;
            File saveDir = new File(userDir);
            if (!saveDir.exists())
                saveDir.mkdirs();
            File dest = new File(saveDir, uuidFileName);

            // 保存文件到磁盘
            file.transferTo(dest);

            // 存储数据库记录
            Files f = new Files();
            f.setFileName(originalName);
            f.setFileType(fileType);
            f.setSize(size);
            f.setPath(userDir + "/" + uuidFileName);
            f.setIsDir(0);
            f.setParentId(parentId);
            f.setOwnerId(ownerId);
            f.setCreatedAt(new Date());

            filesMapper.insert(f);

            res.put("status", "success");
            res.put("message", "上传成功");
        } catch (Exception e) {
            res.put("status", "fail");
            res.put("message", "上传失败：" + e.getMessage());
        }
        return res;
    }

    @Override
    public Map<String, Object> mkdir(Files file) {
        file.setFileType("dir");
        file.setSize(0L);
        file.setPath(null);
        file.setIsDir(1);
        file.setCreatedAt(new Date());
        filesMapper.insert(file);
        Map<String, Object> res = new HashMap<>();
        res.put("status", "success");
        res.put("message", "目录创建成功");
        return res;
    }

    

    @Override
    public Map<String, Object> list(Long parentId, Long ownerId) {
        Map<String, Object> res = new HashMap<>();
        List<Files> filesList = filesMapper.selectByParentIdAndOwnerId(parentId, ownerId);
        if (filesList == null || filesList.isEmpty()) {
            res.put("status", "fail");
            res.put("message", "没有找到相关文件或目录");
            return res;

        }
        res.put("status", "success");
        res.put("files", filesList);
        return res;
    }

    @Override
    public Map<String, Object> deldir(Files file) {
        file = filesMapper.selectByPrimaryKey(file.getId());
        Map<String, Object> res = new HashMap<>();

        if (file == null || file.getId() == null) {
            res.put("status", "fail");
            res.put("message", "文件信息不完整");
            return res;
        }

        file = filesMapper.selectByPrimaryKey(file.getId());
        if (file == null) {
            res.put("status", "fail");
            res.put("message", "文件不存在");
            return res;
        }

        if (file.getIsDir() == 0) {
            // 删除单个文件
            if (filesMapper.deleteByPrimaryKey(file.getId()) > 0) {
                FileManager.delete(file.getPath()); // 删除磁盘文件
                res.put("status", "success");
                res.put("message", "文件删除成功");
            } else {
                res.put("status", "fail");
                res.put("message", "文件删除失败");
            }
            return res;
        } else {
            // 先递归删除目录下所有子内容
            deleteRecursively(file.getId());

            // 再删除该目录本身
            if (filesMapper.deleteByPrimaryKey(file.getId()) > 0) {
                res.put("status", "success");
                res.put("message", "目录及其所有内容删除成功");
            } else {
                res.put("status", "fail");
                res.put("message", "删除目录本身失败");
            }
            return res;
        }
    }

    /**
     * 递归删除指定 parentId 下的所有子文件和文件夹
     */
    private void deleteRecursively(Long parentId) {
        List<Files> filesList = filesMapper.selectByParentId(parentId);

        for (Files file : filesList) {
            if (file.getIsDir() > 0) {
                // 是文件夹 → 递归删除
                deleteRecursively(file.getId());
            }
            // 删除文件或空文件夹
            int result = filesMapper.deleteByPrimaryKey(file.getId());
            if (result > 0) {
                FileManager.delete(file.getPath());// 删除磁盘上的文件
                System.out.println("删除：" + file.getFileName() + " 成功");
            } else {
                throw new RuntimeException("删除文件：" + file.getFileName() + " 失败");
            }
        }
    }

    @Override
    public Map<String, Object> downloadFile(Long id, String userAgent) throws Exception {
        Map<String, Object> res = new HashMap<>();
        Files file = filesMapper.selectByPrimaryKey(id);
        if (file == null || file.getIsDir() == 1) {
            res.put("status", "fail");
            res.put("message", "文件不存在或不能下载目录");
            return res;
        }
        java.io.File realFile = new java.io.File(file.getPath());
        if (!realFile.exists()) {
            res.put("status", "fail");
            res.put("message", "文件不存在");
            return res;
        }
        byte[] fileBytes;
        try (FileInputStream in = new FileInputStream(realFile)) {
            fileBytes = in.readAllBytes();
        }
        String fileName = file.getFileName();
        String encodedFileName;
        if (userAgent != null && userAgent.contains("MSIE")) {
            encodedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
        } else {
            encodedFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        }
        res.put("status", "success");
        res.put("fileName", encodedFileName);
        res.put("fileBytes", fileBytes);
        return res;
    }
}