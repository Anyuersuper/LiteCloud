
package com.litecloud.entity;

import lombok.Data;

/**
 * Users entity
 */
@Data
public class Users {
    /**
     * id
     * @Primary Key
     */
    private Long id;

    /**
     * username
     */
    private String username;

    /**
     * password
     */
    private String password;

    /**
     * role
     */
    private String role;

}
