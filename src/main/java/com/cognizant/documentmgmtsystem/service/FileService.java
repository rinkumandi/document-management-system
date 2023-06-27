package com.cognizant.documentmgmtsystem.service;

import com.cognizant.documentmgmtsystem.dto.FileDTO;
import com.cognizant.documentmgmtsystem.entity.FileEntity;

import java.util.List;

public interface FileService {

    FileEntity uploadFile(FileDTO fileDTO);

    void deleteFile(Long fileId);

    List<FileEntity> getAllFiles();
}

