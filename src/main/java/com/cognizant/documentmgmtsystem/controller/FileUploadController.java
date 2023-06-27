package com.cognizant.documentmgmtsystem.controller;

import com.cognizant.documentmgmtsystem.dto.FileDTO;
import com.cognizant.documentmgmtsystem.entity.FileEntity;
import com.cognizant.documentmgmtsystem.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/files")
@Validated
@Slf4j
public class FileUploadController {

    private final FileService fileService;

    public FileUploadController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") @Valid MultipartFile file
    ) throws IOException {

        // Validate file content type
        if (!file.getContentType().equalsIgnoreCase("application/pdf")) {
            return new ResponseEntity<>("Invalid file format. Only PDF files are allowed.",
                    HttpStatus.BAD_REQUEST);
        }

        FileDTO fileDTO = new FileDTO();
        fileDTO.setName(file.getOriginalFilename());
        fileDTO.setContentType(file.getContentType());
        fileDTO.setData(file.getBytes());

        FileEntity savedFile = fileService.uploadFile(fileDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFile);
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long fileId) {
        fileService.deleteFile(fileId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<FileEntity>> getAllFiles() {
        List<FileEntity> files = fileService.getAllFiles();
        return ResponseEntity.ok(files);
    }
}


