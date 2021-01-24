package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("home")
public class HomeController {

    private final FileService fileService;

    public HomeController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public String homeView(Authentication authentication,
                           Model model) {

        var userName = authentication.getName();
        var fileNamesForUser = this.fileService.getFileNamesForUser(userName);
        model.addAttribute("fileNames", fileNamesForUser);

        return "home";
    }

    @PostMapping("upload-file")
    public String handleFileUpload(Authentication authentication,
                                   @RequestParam("fileUpload") MultipartFile fileUpload,
                                   Model model) {

        String userName = authentication.getName();

        try {
            this.fileService.saveFile(fileUpload, userName);
            model.addAttribute("errorType", -1);

        } catch (IOException e) {
            model.addAttribute("errorType", 1);
            e.printStackTrace();
        }

        return "result";
    }

    @GetMapping("delete-file/{fileName}")
    public String deleteFile(Authentication authentication,
                             @PathVariable String fileName) {

        var userName = authentication.getName();
        this.fileService.deleteFile(fileName, userName);

        return "redirect:/home";
    }
}
