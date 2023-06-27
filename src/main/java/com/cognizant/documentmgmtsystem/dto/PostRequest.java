package com.cognizant.documentmgmtsystem.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostRequest {
    private String title;
    private String body;

}
