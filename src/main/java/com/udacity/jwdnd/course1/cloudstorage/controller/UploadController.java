package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;


@Controller
public class UploadController {
    private NoteService noteService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;
    private UserService userService;
    private FileService fileService;

    public UploadController(UserService userService,
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

    @PostMapping("/file-upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication, File file, Note note, Credential credential, Model model) {
        String uploadError = null;
        User user = this.userService.getUser(authentication.getName());
        String fileName = fileUpload.getOriginalFilename();
        final byte[] bytes = new byte[1024];
        int read = 0;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            java.io.File path = new java.io.File("C:\\STORAGE\\" + user.getUsername());
            if (!path.exists()) path.mkdir();
            java.io.File newFile = new java.io.File("C:\\STORAGE\\" + user.getUsername() + "\\" + fileName);
            if (newFile.exists()) {
                uploadError = "You already uploaded the file: " + fileName;
                model.addAttribute("uploadError", uploadError);
                model.addAttribute("encryptionService", encryptionService);
                model.addAttribute("notes", this.noteService.getNotes(user.getUserId()));
                model.addAttribute("credentials", this.credentialService.getCredentials(user.getUserId()));
                model.addAttribute("files", this.fileService.getFiles(user.getUserId()));
                return "home";
            }
            fos = new FileOutputStream(newFile);
            is = fileUpload.getInputStream();
            while((read = is.read(bytes))!=-1) {
                fos.write(bytes, 0, read);
            }
            file.setFileName(fileUpload.getOriginalFilename());
            file.setFileSize(newFile.length()+"");
            file.setUserId(user.getUserId());
            URLConnection connection = newFile.toURL().openConnection();
            file.setContentType(connection.getContentType());
            fileService.addFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("notes", this.noteService.getNotes(user.getUserId()));
        model.addAttribute("credentials", this.credentialService.getCredentials(user.getUserId()));
        model.addAttribute("files", this.fileService.getFiles(user.getUserId()));
        return "home";
    }

    @GetMapping("/files/delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") Integer fileId,
                                    Authentication authentication,
                                    File file, Note note,
                                    Credential credential, Model model) {
        User user = this.userService.getUser(authentication.getName());
        String fileName = fileService.getFile(fileId).getFileName();
        java.io.File newFile = new java.io.File("C:\\STORAGE\\" + user.getUsername() + "\\" + fileName);
        boolean done = newFile.delete();
        if (done) {
            this.fileService.deleteFile(fileId);
        }
        model.addAttribute("notes", this.noteService.getNotes(user.getUserId()));
        model.addAttribute("credentials", this.credentialService.getCredentials(user.getUserId()));
        model.addAttribute("files", this.fileService.getFiles(user.getUserId()));
        return "home";
    }

    @GetMapping("/files/view/{fileId}")
    public ResponseEntity<Resource> viewFile(@PathVariable("fileId") Integer fileId,
                                             Authentication authentication,
                                             File file, Note note,
                                             Credential credential, Model model) throws Exception
    {
        User user = this.userService.getUser(authentication.getName());
        String fileName = fileService.getFile(fileId).getFileName();
        java.io.File newFile = new java.io.File("C:\\STORAGE\\" + user.getUsername() + "\\" + fileName);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "inline;filename="+file.getFileName());

        Path path = Paths.get(newFile.getAbsolutePath());

        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(newFile.length())
                .contentType(MediaType.parseMediaType(fileService.getFile(fileId).getContentType()))
                .body(resource);
    }
}