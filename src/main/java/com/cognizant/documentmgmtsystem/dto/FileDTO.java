package com.cognizant.documentmgmtsystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDTO {

    private String name;
    private String contentType;
    private byte[] data;

}

