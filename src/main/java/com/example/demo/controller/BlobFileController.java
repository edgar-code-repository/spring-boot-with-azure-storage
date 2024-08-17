package com.example.demo.controller;


import com.example.demo.dto.FileListResponseDTO;
import com.example.demo.dto.FileResponseDTO;
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
        return ResponseEntity.ok(blobFileService.getListOfFiles(containerName));
    }

    @RequestMapping("/files/{containerName}/{fileName}")
    public ResponseEntity<FileResponseDTO> getFileFromContainer(@PathVariable String containerName, @PathVariable String fileName) {
        log.debug("[getListOfFilesFromContainer][START][containerName: {}]", containerName);
        log.debug("[getListOfFilesFromContainer][START][fileName: {}]", fileName);

        String filePath = "countries";
        return ResponseEntity.ok(blobFileService.getFileFromContainer(containerName, filePath + "/" + fileName));
    }

}
