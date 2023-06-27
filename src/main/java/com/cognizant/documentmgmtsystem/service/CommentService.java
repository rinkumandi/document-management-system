package com.cognizant.documentmgmtsystem.service;

import com.cognizant.documentmgmtsystem.dto.CommentDTO;

import java.io.FileNotFoundException;

public interface CommentService {

    CommentDTO createComment(final CommentDTO commentDTO) throws FileNotFoundException;
}

