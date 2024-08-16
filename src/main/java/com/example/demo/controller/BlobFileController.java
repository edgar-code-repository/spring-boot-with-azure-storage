package com.example.demo.controller;


import com.example.demo.dto.FileListResponseDTO;
import com.example.demo.service.BlobFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class BlobFileController {

    @Autowired
    private BlobFileService blobFileService;

    @RequestMapping("/files/{containerName}")
    public ResponseEntity<FileListResponseDTO> getListOfFilesFromContainer(@PathVariable String containerName) {
        log.debug("[getListOfFilesFromContainer][START][containerName: {}]", containerName);

        List<String> listOfFiles = blobFileService.getListOfFiles(containerName);
        FileListResponseDTO bodyResponse = FileListResponseDTO.builder()
                .containerName(containerName)
                .fileListSize(listOfFiles.size())
                .fileList(listOfFiles)
                .build();

        log.debug("[getListOfFilesFromContainer][END]");
        return ResponseEntity.ok(bodyResponse);
    }

}
