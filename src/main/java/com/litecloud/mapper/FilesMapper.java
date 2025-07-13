
package com.litecloud.mapper;

import com.litecloud.entity.Files;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FilesMapper {
    int insert(Files record);
    int deleteByPrimaryKey(Long id);
    int updateByPrimaryKey(Files record);
    Files selectByPrimaryKey(Long id);
    List<Files> selectAll();
    Files selectByFileName(String fileName);
    Files selectRootByUserId(Long userId);
    List<Files> selectByParentId(Long parentId);
    List<Files> selectByParentIdAndOwnerId(Long parentId, Long ownerId);
}