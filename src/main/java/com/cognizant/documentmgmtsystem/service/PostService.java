package com.cognizant.documentmgmtsystem.service;

import com.cognizant.documentmgmtsystem.dto.PostDTO;

import java.io.FileNotFoundException;
import java.util.List;

public interface PostService {

    PostDTO createPost(PostDTO postDTO) throws FileNotFoundException;

    List<PostDTO> getPostsByFile(Long fileId) throws FileNotFoundException;
}

