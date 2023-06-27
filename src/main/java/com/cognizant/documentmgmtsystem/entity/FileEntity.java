package com.cognizant.documentmgmtsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "content_type")
    private String contentType;

    @Lob
    private byte[] data;

    @OneToOne(mappedBy = "file")
    private PostEntity post;

    @OneToOne(mappedBy = "file")
    private CommentEntity comment;

}

