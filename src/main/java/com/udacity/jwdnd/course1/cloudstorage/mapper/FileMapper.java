package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<File> getAllFiles(Integer userId);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFile(Integer fileId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid) VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Update("UPDATE FILES SET filename = #{fileName}, contenttype = #{contentType}, filesize = #{fileSize}, userid = #{userId}, filedata = #{fileDate} WHERE fileId = #{fileId}")
    int update(File file);

    @Delete("DELETE FILES WHERE fileId = #{fileId}")
    int delete(Integer fileId);
}
