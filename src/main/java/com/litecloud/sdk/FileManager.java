package com.litecloud.sdk;

import java.io.File;

public class FileManager {

    /**
     * 删除指定路径的文件或空目录（不递归删除）
     *
     * @param path 文件或空目录的路径
     * @return 是否删除成功
     */
    public static boolean delete(String path) {
        if (path == null || path.trim().isEmpty()) {
            System.err.println("[删除失败] 路径为空");
            return false;
        }

        File file = new File(path);

        if (!file.exists()) {
            System.out.println("[提示] 文件不存在：" + path);
            return true; // 已不存在，视为删除成功
        }

        boolean success = file.delete();
        if (success) {
            System.out.println("[删除成功] " + path);
        } else {
            System.err.println("[删除失败] 无法删除：" + path);
        }
        return success;
    }

    /**
     * 确保用户目录存在，不存在则创建
     */
    public static boolean mkdir(String userDirPath) {
        File userDir = new File(userDirPath);
        if (!userDir.exists()) {
            return userDir.mkdirs();
        }
        return true;
    }

    /**
     * 删除用户目录及其内容
     *
     * @param dir 用户目录路径
     */

    public static boolean deleteDir(String dir) {
        File fileDir = new File(dir);
        if (fileDir.exists() && fileDir.isDirectory()) {
            File[] files = fileDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDir(file.getAbsolutePath());
                    } else {
                        if (!file.delete()) {
                            return false;
                        }
                    }
                }
            }
            fileDir.delete();
        }
        return true;
    }
}
