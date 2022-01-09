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
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        if((fileUpload.getSize() > 1000000)) {
            uploadError = "File size exceed limit!";
            model.addAttribute("errorMessage", uploadError);
            model.addAttribute("error", Boolean.TRUE);
            return "result";
        }
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
                model.addAttribute("errorMessage", uploadError);
                model.addAttribute("error", Boolean.TRUE);
                return "result";
            }
            fos = new FileOutputStream(newFile);
            is = fileUpload.getInputStream();
            while ((read = is.read(bytes)) != -1) {
                fos.write(bytes, 0, read);
            }
            file.setFileData(bytes);
            file.setFileName(fileUpload.getOriginalFilename());
            file.setFileSize(newFile.length() + "");
            file.setUserId(user.getUserId());
            URLConnection connection = newFile.toURL().openConnection();
            file.setContentType(connection.getContentType());
            fileService.addFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            uploadError = e.getMessage();
            model.addAttribute("errorMessage", uploadError);
            model.addAttribute("error", Boolean.TRUE);
            return "result";
        } finally {
            try {
                if (is != null) is.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                uploadError = e.getMessage();
                e.printStackTrace();
                model.addAttribute("errorMessage", uploadError);
                model.addAttribute("error", Boolean.TRUE);
                return "result";
            }
        }
        model.addAttribute("errorMessage", uploadError);
        model.addAttribute("success", Boolean.TRUE);
        return "result";
    }

    @GetMapping("/files/delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") Integer fileId,
                                    Authentication authentication,
                                    File file, Note note,
                                    Credential credential, Model model) {
        String errorMessage = "Failed to delete the file.";
        User user = this.userService.getUser(authentication.getName());
        String fileName = fileService.getFile(fileId).getFileName();
        java.io.File newFile = new java.io.File("C:\\STORAGE\\" + user.getUsername() + "\\" + fileName);
        try {
            Files.deleteIfExists(newFile.toPath());
            this.fileService.deleteFile(fileId);
        } catch (IOException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("error", Boolean.TRUE);
            return "result";
        }
        model.addAttribute("success", Boolean.TRUE);
        return "result";
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

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleFileSizeExceededError(RedirectAttributes redirectAttributes){
        String uploadError = "ERROR: File size exceeded the limit of 5MB";
        redirectAttributes.addAttribute("errorMessage", uploadError);
        redirectAttributes.addAttribute("error", Boolean.TRUE);
        return "result";
    }
}
