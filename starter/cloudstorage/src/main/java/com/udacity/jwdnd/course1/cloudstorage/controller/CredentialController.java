package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("credential")
public class CredentialController {

    private CredentialService credentialService;
    private UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping("add-credential")
    public String addCredential(Authentication authentication,
                                @ModelAttribute("newCredential") CredentialForm newCredential,
                                Model model) {

        var userName = authentication.getName();

        if (newCredential.getCredentialId().isEmpty()) {
            this.credentialService.addCredential(newCredential, userName);
        } else {
            this.credentialService.updateCredential(newCredential, userName);
        }

        model.addAttribute("success", true);
        return "result";
    }

    @GetMapping("delete-credential/{credentialId}")
    public String deleteCredential(Authentication authentication,
                                   @PathVariable int credentialId,
                                   Model model) {

        var userName = authentication.getName();
        this.credentialService.deleteCredentialById(credentialId, userName);

        model.addAttribute("success", true);
        return "result";
    }

}
