
package com.litecloud.entity;

import lombok.Data;
import java.util.Date;

/**
 * Files entity
 */
@Data
public class Files {
    /**
     * id
     * @Primary Key
     */
    private Long id;

    /**
     * file_name
     */
    private String fileName;

    /**
     * file_type
     */
    private String fileType;

    /**
     * size
     */
    private Long size;

    /**
     * path
     */
    private String path;

    /**
     * is_dir
     */
    private Integer isDir;

    /**
     * parent_id
     */
    private Long parentId;

    /**
     * owner_id
     */
    private Long ownerId;

    /**
     * created_at
     */
    private Date createdAt;

}
