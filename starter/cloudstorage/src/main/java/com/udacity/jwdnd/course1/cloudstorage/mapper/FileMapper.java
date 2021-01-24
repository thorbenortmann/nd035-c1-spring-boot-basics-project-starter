package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT filename FROM FILES WHERE userid=#{userid}")
    List<String> getFileNamesForUser(Integer userid);

    @Select("SELECT * FROM FILES WHERE filename=#{filename} AND userid=#{userid}")
    File getByFileNameAndUserId(String filename, Integer userid);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileid")
    int insert(File file);

    @Delete("DELETE FROM FILES WHERE filename=#{filename} AND userid=#{userid}")
    void deleteByFileNameAndUserId(String filename, Integer userid);
}
