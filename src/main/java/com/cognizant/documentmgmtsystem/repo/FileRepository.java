package com.cognizant.documentmgmtsystem.repo;

import com.cognizant.documentmgmtsystem.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

}

