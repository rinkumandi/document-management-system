package com.cognizant.documentmgmtsystem.service;

import com.cognizant.documentmgmtsystem.dto.CommentDTO;
import com.cognizant.documentmgmtsystem.entity.CommentEntity;
import com.cognizant.documentmgmtsystem.entity.FileEntity;
import com.cognizant.documentmgmtsystem.feign.JSONPlaceholderClient;
import com.cognizant.documentmgmtsystem.repo.CommentRepository;
import com.cognizant.documentmgmtsystem.repo.FileRepository;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final JSONPlaceholderClient jsonPlaceholderClient;
    private final FileRepository fileRepository;

    public CommentServiceImpl(
            CommentRepository commentRepository,
            JSONPlaceholderClient jsonPlaceholderClient, FileRepository fileRepository) {
        this.commentRepository = commentRepository;
        this.jsonPlaceholderClient = jsonPlaceholderClient;
        this.fileRepository = fileRepository;
    }

    @Override
    public CommentDTO createComment(final CommentDTO commentDTO) throws FileNotFoundException {
        final CommentDTO commentDtoCreated = jsonPlaceholderClient.createComment(commentDTO);
       final FileEntity fileEntity = fileRepository.findById(commentDTO.getFileId())
                .orElseThrow(FileNotFoundException::new);
        final CommentEntity entityToBeSaved = buildCommentEntity(commentDtoCreated, fileEntity);
        final CommentEntity commentEntity = commentRepository.save(entityToBeSaved);
        return buildCommentDto(commentEntity);

    }

    private static CommentDTO buildCommentDto(CommentEntity commentEntity) {
        return CommentDTO.builder()
                .id(commentEntity.getId())
                .name(commentEntity.getName())
                .body(commentEntity.getBody())
                .email(commentEntity.getEmail())
                .fileId(commentEntity.getFile().getId())
                .build();
    }

    private static CommentEntity buildCommentEntity(CommentDTO commentDtoCreated, FileEntity fileEntity) {
        return CommentEntity.builder()
                .name(commentDtoCreated.getName())
                .body(commentDtoCreated.getBody())
                .email(commentDtoCreated.getEmail())
                .file(fileEntity)
                .build();
    }
}

