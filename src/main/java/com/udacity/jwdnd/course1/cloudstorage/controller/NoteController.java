package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class NoteController {

    private NoteService noteService;
    private UserService userService;
    private CredentialService credentialService;
    private Integer noteId;
    private FileService fileService;

    public NoteController(UserService userService, NoteService noteService, CredentialService credentialService,
                          FileService fileService) {
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.fileService = fileService;
    }

    @PostMapping("/notes/update")
    public String postNoteMessage(Authentication authentication, Note note, Credential credential, Model model) {
        User user = this.userService.getUser(authentication.getName());
        note.setUserId(user.getUserId());
        if (note.getNoteId() == null)
            this.noteService.addNote(note);
        else
            this.noteService.updateNote(note);
        note.setNoteTitle("");
        note.setNoteDescription("");
        model.addAttribute("credentials", this.credentialService.getCredentials(user.getUserId()));
        model.addAttribute("notes", this.noteService.getNotes(user.getUserId()));
        model.addAttribute("files", this.fileService.getFiles(user.getUserId()));
        return "home";
    }

    @GetMapping("/notes/delete/{noteId}")
    public String deleteNote(@PathVariable("noteId") Integer noteId,
                                    Authentication authentication,
                                    Credential credential,
                                    Note note, Model model) {
        User user = this.userService.getUser(authentication.getName());
        note.setUserId(user.getUserId());
        this.noteService.deleteNote(noteId);
        model.addAttribute("credentials", this.credentialService.getCredentials(user.getUserId()));
        model.addAttribute("notes", this.noteService.getNotes(user.getUserId()));
        model.addAttribute("files", this.fileService.getFiles(user.getUserId()));
        return "home";
    }
}
