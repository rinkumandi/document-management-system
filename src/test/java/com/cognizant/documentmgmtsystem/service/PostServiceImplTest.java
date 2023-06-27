package com.cognizant.documentmgmtsystem.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cognizant.documentmgmtsystem.dto.PostDTO;
import com.cognizant.documentmgmtsystem.entity.FileEntity;
import com.cognizant.documentmgmtsystem.entity.PostEntity;
import com.cognizant.documentmgmtsystem.feign.JSONPlaceholderClient;
import com.cognizant.documentmgmtsystem.repo.FileRepository;
import com.cognizant.documentmgmtsystem.repo.PostRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private JSONPlaceholderClient jsonPlaceholderClient;

    @Mock
    private FileRepository fileRepository;


    @InjectMocks
    private PostServiceImpl postService;

    @Test
    public void testGetPostById() throws FileNotFoundException {
        // Mock data
        long fileId = 1L;

        // Mock repository method
        final FileEntity fileEntity = buildFileEntity(fileId);
        when(fileRepository.findById(fileId)).thenReturn(Optional.ofNullable(fileEntity));

        List<PostEntity> postEntityList = new ArrayList<>();
        final PostEntity postEntity = buildPostEntity(fileEntity);
        postEntityList.add(postEntity);
        when(postRepository.findByFile(fileEntity)).thenReturn(postEntityList);

        // Call service method
        final List<PostDTO> result = postService.getPostsByFile(fileId);

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.size());

        assertEquals(postEntity.getBody(), result.get(0).getBody());
        assertEquals(postEntity.getTitle(), result.get(0).getTitle());

        // Verify repository method was called
        verify(fileRepository, times(1)).findById(fileId);
        verify(postRepository, times(1)).findByFile(fileEntity);
    }

    private FileEntity buildFileEntity(final Long id) {
        return FileEntity.builder()
                .name("testName1")
                .id(id)
                .name("testName1")
                .build();
    }

    private PostEntity buildPostEntity(final FileEntity fileEntity) {
        return PostEntity.builder()
                .title("testPostTitle1")
                .body("testPostBody1")
                .file(fileEntity)
                .build();
    }


}
