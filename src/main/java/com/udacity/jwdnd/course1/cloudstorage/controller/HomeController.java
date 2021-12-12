package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private NoteService noteService;
    private CredentialService credentialService;
    private UserService userService;
    private FileService fileService;
    private EncryptionService encryptionService;

    public HomeController(UserService userService, NoteService noteService,
                          CredentialService credentialService,
                          FileService fileService,
                          EncryptionService encryptionService) {
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.fileService = fileService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String getHomePage(Authentication authentication, Note note, Credential credential, Model model) {
        User user = this.userService.getUser(authentication.getName());

        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("notes", this.noteService.getNotes(user.getUserId()));
        model.addAttribute("credentials", this.credentialService.getCredentials(user.getUserId()));
        model.addAttribute("files", this.fileService.getFiles(user.getUserId()));
        return "home";
    }
}
