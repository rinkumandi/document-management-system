package com.cognizant.documentmgmtsystem.service;

import com.cognizant.documentmgmtsystem.dto.PostDTO;
import com.cognizant.documentmgmtsystem.entity.FileEntity;
import com.cognizant.documentmgmtsystem.entity.PostEntity;
import com.cognizant.documentmgmtsystem.feign.JSONPlaceholderClient;
import com.cognizant.documentmgmtsystem.repo.FileRepository;
import com.cognizant.documentmgmtsystem.repo.PostRepository;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {


    private final PostRepository postRepository;
    private final JSONPlaceholderClient jsonPlaceholderClient;
    private final FileRepository fileRepository;

    public PostServiceImpl(
            final PostRepository postRepository, final JSONPlaceholderClient jsonPlaceholderClient,
            final FileRepository fileRepository) {

        this.postRepository = postRepository;
        this.jsonPlaceholderClient = jsonPlaceholderClient;
        this.fileRepository = fileRepository;
    }

    public PostDTO createPost(final PostDTO postDTO) throws FileNotFoundException {
        final PostDTO createdPost = jsonPlaceholderClient.createPost(postDTO);
        final FileEntity fileEntity = fileRepository.findById(createdPost.getFileId())
                .orElseThrow(FileNotFoundException::new);
        final PostEntity postEntity = buildPostEntity(createdPost, fileEntity);
        final List<PostEntity> postEntityList = postRepository.findByFile(fileEntity);
        PostEntity savedPostEntity;
        if(postEntityList.isEmpty()) {
            savedPostEntity = postRepository.save(postEntity);
        } else {
            final PostEntity postEntity1 = postEntityList.get(0);
            postEntity1.setBody(postEntity.getBody());
            postEntity1.setFile(postEntity.getFile());
            postEntity1.setTitle(postEntity.getTitle());
            savedPostEntity = postRepository.save(postEntity1);
        }
        return buildPostDTO(savedPostEntity);
    }

    @Override
    public List<PostDTO> getPostsByFile(final Long fileId) throws FileNotFoundException {
        final FileEntity fileEntity = fileRepository.findById(fileId)
                .orElseThrow(FileNotFoundException::new);
        final List<PostEntity> postEntityList = postRepository.findByFile(fileEntity);
        final List<PostDTO> postDTOList = new ArrayList<>();
        for (PostEntity postEntity : postEntityList) {
            final PostDTO postDTO = buildPostDTO(postEntity);
            postDTOList.add(postDTO);
        }
        return postDTOList;
    }

    private static PostEntity buildPostEntity(PostDTO createdPost, FileEntity fileEntity) {
        return PostEntity.builder()
                .title(createdPost.getTitle())
                .body(createdPost.getBody())
                .file(fileEntity)
                .build();
    }

    private static PostDTO buildPostDTO(PostEntity savedPostEntity) {
        return PostDTO.builder()
                .id(savedPostEntity.getId())
                .title(savedPostEntity.getTitle())
                .body(savedPostEntity.getBody())
                .fileId(savedPostEntity.getFile().getId())
                .build();
    }
}

