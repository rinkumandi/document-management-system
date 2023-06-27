package com.cognizant.documentmgmtsystem.controller;

import com.cognizant.documentmgmtsystem.dto.CommentDTO;
import com.cognizant.documentmgmtsystem.dto.CommentRequest;
import com.cognizant.documentmgmtsystem.dto.PostDTO;
import com.cognizant.documentmgmtsystem.dto.PostRequest;
import com.cognizant.documentmgmtsystem.service.CommentService;
import com.cognizant.documentmgmtsystem.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/documents")
@Slf4j
public class PostCommentController {

    private PostService postService;
    private CommentService commentService;

    public PostCommentController(final PostService postService, final CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @PostMapping("/{fileId}/posts")
    public ResponseEntity<PostDTO> createPost(
            @PathVariable("fileId") Long fileId, @RequestBody @Valid PostRequest postRequest) {

        final PostDTO postDTO = buildPostDto(fileId, postRequest);
        try {
            final PostDTO response = postService.createPost(postDTO);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (FileNotFoundException e) {
            log.error("Couldn't create post duel to {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{fileId}/posts")
    public ResponseEntity<List<PostDTO>> getPostsForDocument(@PathVariable("fileId") Long fileId) {

        try {
            final List<PostDTO> postDTOList = postService.getPostsByFile(fileId);
            return new ResponseEntity<>(postDTOList, HttpStatus.CREATED);
        } catch (FileNotFoundException e) {
            log.error("Couldn't find document/file with the given id {}", fileId);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{fileId}/comments")
    public ResponseEntity<CommentDTO> createComment(
            @PathVariable("fileId") Long fileId,
            @RequestBody @Valid CommentRequest commentRequest) {
        // Associate the comment with the specified document
        final CommentDTO commentDTO = buildCommentDto(fileId, commentRequest);
        try {
            final CommentDTO response = commentService.createComment(commentDTO);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (FileNotFoundException e) {
            log.error("Couldn't create comment duel to {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static CommentDTO buildCommentDto(Long fileId, CommentRequest commentRequest) {
        return CommentDTO.builder()
                .fileId(fileId)
                .name(commentRequest.getName())
                .email(commentRequest.getEmail())
                .body(commentRequest.getBody())
                .build();
    }

    private static PostDTO buildPostDto(Long fileId, PostRequest postRequest) {
        return PostDTO.builder()
                .fileId(fileId)
                .title(postRequest.getTitle())
                .body(postRequest.getBody())
                .build();
    }
}

