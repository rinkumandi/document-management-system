package com.cognizant.documentmgmtsystem.service;

import com.cognizant.documentmgmtsystem.dto.FileDTO;
import com.cognizant.documentmgmtsystem.entity.FileEntity;
import com.cognizant.documentmgmtsystem.repo.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public FileEntity uploadFile(FileDTO fileDTO) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setName(fileDTO.getName());
        fileEntity.setContentType(fileDTO.getContentType());
        fileEntity.setData(fileDTO.getData());

        return fileRepository.save(fileEntity);
    }

    @Override
    public void deleteFile(Long fileId) {
        fileRepository.deleteById(fileId);
    }

    @Override
    public List<FileEntity> getAllFiles() {
        return fileRepository.findAll();
    }
}

