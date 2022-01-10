package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.security.SecureRandom;
import java.util.Base64;

@Controller
public class CredentialController {

    private NoteService noteService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;
    private UserService userService;
    private FileService fileService;

    public CredentialController(UserService userService,
                                CredentialService credentialService,
                                EncryptionService encryptionService,
                                NoteService noteService,
                                FileService fileService) {
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.fileService = fileService;
    }

    @PostMapping("/credentials/update")
    public String postCredentials(Authentication authentication, Note note, Credential credential, Model model) {
        User user = this.userService.getUser(authentication.getName());
        credential.setUserId(user.getUserId());

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];

        String encodedKey = null;
        if (credential.getCredentialId() == null) {
            random.nextBytes(key);
            encodedKey = Base64.getEncoder().encodeToString(key);
        } else {
            encodedKey = credentialService.getCredentialKey(credential.getCredentialId());
        }

        String encodedPassword = this.encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(encodedPassword);

        if (credential.getCredentialId() == null)
            this.credentialService.addCredential(credential);
        else
            this.credentialService.updateCredential(credential);
        model.addAttribute("success", Boolean.TRUE);
        return "result";
    }

    @GetMapping("/credentials/delete/{credentialId}")
    public String deleteCredential(@PathVariable("credentialId") Integer credentialId,
                                    Authentication authentication, Note note,
                                    Credential credential, Model model) {
        User user = this.userService.getUser(authentication.getName());
        credential.setUserId(user.getUserId());
        this.credentialService.deleteCredential(credentialId);
        model.addAttribute("success", Boolean.TRUE);
        return "result";
    }
}
