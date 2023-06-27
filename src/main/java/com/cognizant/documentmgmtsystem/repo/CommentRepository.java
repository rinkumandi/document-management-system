package com.cognizant.documentmgmtsystem.repo;

import com.cognizant.documentmgmtsystem.entity.CommentEntity;
import com.cognizant.documentmgmtsystem.entity.FileEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByFile(FileEntity file);
}

