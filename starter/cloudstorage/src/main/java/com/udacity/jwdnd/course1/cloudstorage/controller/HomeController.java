package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("home")
public class HomeController {

    private final FileService fileService;
    private final NoteService noteService;

    public HomeController(FileService fileService, NoteService noteService) {
        this.fileService = fileService;
        this.noteService = noteService;
    }

    @GetMapping
    public String homeView(Authentication authentication,
                           @ModelAttribute("newNote") NoteForm newNote,
                           Model model) {

        var userName = authentication.getName();

        var fileNamesForUser = this.fileService.getFileNamesForUser(userName);
        var notesForUser = this.noteService.getNotesForUser(userName);

        model.addAttribute("fileNames", fileNamesForUser);
        model.addAttribute("notes", notesForUser);

        return "home";
    }

}
