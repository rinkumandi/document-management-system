package com.cognizant.documentmgmtsystem.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentDTO {
    private Long id;
    private Long postId;
    private String name;
    private String email;
    private String body;

    private Long fileId;

}
