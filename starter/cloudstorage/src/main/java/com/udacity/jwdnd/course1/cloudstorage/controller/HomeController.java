package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
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

    private final CredentialService credentialService;
    private final EncryptionService encryptionService;
    private final FileService fileService;
    private final NoteService noteService;

    public HomeController(CredentialService credentialService, EncryptionService encryptionService, FileService fileService, NoteService noteService) {
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.fileService = fileService;
        this.noteService = noteService;
    }

    @GetMapping
    public String homeView(Authentication authentication,
                           @ModelAttribute("newCredential") CredentialForm newCredential,
                           @ModelAttribute("newNote") NoteForm newNote,
                           Model model) {

        var userName = authentication.getName();

        var credentialsForUser = this.credentialService.getCredentialsForUser(userName);
        var notesForUser = this.noteService.getNotesForUser(userName);
        var fileNamesForUser = this.fileService.getFileNamesForUser(userName);

        model.addAttribute("credentials", credentialsForUser);
        model.addAttribute("fileNames", fileNamesForUser);
        model.addAttribute("notes", notesForUser);
        model.addAttribute("encryptionService", this.encryptionService);

        return "home";
    }

}
