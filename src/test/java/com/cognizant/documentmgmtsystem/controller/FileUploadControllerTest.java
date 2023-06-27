package com.cognizant.documentmgmtsystem.controller;

import com.cognizant.documentmgmtsystem.controller.FileUploadController;
import com.cognizant.documentmgmtsystem.entity.FileEntity;
import com.cognizant.documentmgmtsystem.service.FileService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class FileUploadControllerTest {


    @Test
    void testUploadFile() throws Exception {
        // Mocking the FileService
        FileService fileService = mock(FileService.class);
        when(fileService.uploadFile(any())).thenReturn(new FileEntity());

        // Creating the MockMultipartFile
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                "PDF content".getBytes()
        );

        // Creating the controller instance and setting up the MockMvc
        FileUploadController fileUploadController = new FileUploadController(fileService);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(fileUploadController).build();

        // Performing the file upload request
        mockMvc.perform(MockMvcRequestBuilders.multipart("/files")
                        .file(file))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testUploadFile_InvalidExtension() throws Exception {
        // Mocking the FileService
        FileService fileService = mock(FileService.class);

        // Creating the MockMultipartFile with an invalid file extension
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Text content".getBytes()
        );

        // Creating the controller instance and setting up the MockMvc
        FileUploadController fileUploadController = new FileUploadController(fileService);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(fileUploadController).build();

        // Performing the file upload request with an invalid file extension
        mockMvc.perform(MockMvcRequestBuilders.multipart("/files")
                        .file(file))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testDeleteFile() throws Exception {
        // Mocking the FileService
        FileService fileService = mock(FileService.class);

        // Creating the controller instance and setting up the MockMvc
        FileUploadController fileUploadController = new FileUploadController(fileService);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(fileUploadController).build();

        // Performing the file delete request
        mockMvc.perform(MockMvcRequestBuilders.delete("/files/{fileId}", 1L))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testGetAllFiles() throws Exception {
        // Mocking the FileService
        FileService fileService = mock(FileService.class);
        when(fileService.getAllFiles()).thenReturn(Collections.singletonList(new FileEntity()));

        // Creating the controller instance and setting up the MockMvc
        FileUploadController fileUploadController = new FileUploadController(fileService);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(fileUploadController).build();

        // Performing the get all files request
        mockMvc.perform(MockMvcRequestBuilders.get("/files"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

