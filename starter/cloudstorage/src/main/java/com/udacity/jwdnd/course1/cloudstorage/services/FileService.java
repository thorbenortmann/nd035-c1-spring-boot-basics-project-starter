package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;
    private UserMapper userMapper;

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    public void saveFile(MultipartFile fileUpload, String userName) throws IOException {

        var user = this.userMapper.getUser(userName);

        var userId = user.getUserId();
        var fileName = fileUpload.getOriginalFilename();
        var contentType = fileUpload.getContentType();
        var fileSize = String.valueOf(fileUpload.getSize());
        var fileBytes = fileUpload.getBytes();

        this.fileMapper.insert(new File(null, fileName, contentType, fileSize, userId, fileBytes));

    }

    public List<String> getFileNamesForUser(String userName) {
        var userId = this.userMapper.getUser(userName).getUserId();
        return this.fileMapper.getFileNamesForUser(userId);
    }

    public void deleteFile(String fileName, String userName) {
        var userId = this.userMapper.getUser(userName).getUserId();
        this.fileMapper.deleteByFileNameAndUserId(fileName, userId);
    }
}
