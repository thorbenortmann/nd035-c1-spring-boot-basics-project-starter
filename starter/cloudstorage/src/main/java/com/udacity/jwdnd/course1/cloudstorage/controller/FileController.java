package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.DuplicateFileNameException;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Controller
@RequestMapping("file")
public class FileController {

    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("upload-file")
    public String uploadFile(Authentication authentication,
                             @RequestParam("fileUpload") MultipartFile fileUpload,
                             Model model) {

        String userName = authentication.getName();

        try {
            this.fileService.saveFile(fileUpload, userName);
            model.addAttribute("success", true);

        } catch (DuplicateFileNameException | IOException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", e.toString());
            model.addAttribute("success", false);
        }

        return "result";
    }

    @GetMapping("view-file/{fileName}")
    public ResponseEntity<InputStreamResource> viewFile(Authentication authentication,
                                                        @PathVariable String fileName) {
        var userName = authentication.getName();
        var file = fileService.getFile(fileName, userName);

        var contentType = MediaType.valueOf(file.getContenttype());
        var resource = new InputStreamResource(new ByteArrayInputStream(file.getFiledata()));

        return ResponseEntity.ok()
                .contentType(contentType)
                .body(resource);
    }

    @GetMapping("delete-file/{fileName}")
    public String deleteFile(Authentication authentication,
                             @PathVariable String fileName) {

        var userName = authentication.getName();
        this.fileService.deleteFile(fileName, userName);

        return "redirect:/home";
    }
}
