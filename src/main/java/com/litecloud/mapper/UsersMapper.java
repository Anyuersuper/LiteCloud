
package com.litecloud.mapper;

import com.litecloud.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.tomcat.jni.User;

import java.util.List;

@Mapper
public interface UsersMapper {
    int insert(Users record);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(Users record);

    Users selectByPrimaryKey(Long id);

    List<Users> selectAll();

    Users login(String username, String password);

    List<Users> listUsers(int offset, int size);
}