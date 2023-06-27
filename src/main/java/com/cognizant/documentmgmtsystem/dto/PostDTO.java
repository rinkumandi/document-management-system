package com.cognizant.documentmgmtsystem.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostDTO {
    private Long id;
    private Long fileId;
    private String title;
    private String body;

}
