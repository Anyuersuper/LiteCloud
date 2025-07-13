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
}
