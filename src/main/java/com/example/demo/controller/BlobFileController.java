package com.example.demo.controller;


import com.example.demo.dto.FileListResponseDTO;
import com.example.demo.dto.FileRequestDTO;
import com.example.demo.dto.FileResponseDTO;
import com.example.demo.service.BlobFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class BlobFileController {

    @Autowired
    private BlobFileService blobFileService;

    @GetMapping("/files/{containerName}")
    public ResponseEntity<FileListResponseDTO> getListOfFilesFromContainer(@PathVariable String containerName) {
        log.debug("[getListOfFilesFromContainer][START][containerName: {}]", containerName);
        return ResponseEntity.ok(blobFileService.getListOfFiles(containerName));
    }

    @PostMapping("/files/{containerName}")
    public ResponseEntity<FileResponseDTO> getFileFromContainer(@RequestBody FileRequestDTO fileRequestDTO) {
        log.debug("[getFileFromContainer][START][fileRequestDTO: {}]", fileRequestDTO.toString());
        return ResponseEntity.ok(blobFileService.getFileFromContainer(
                fileRequestDTO.getContainerName(), fileRequestDTO.getFilePathAndName()));
    }

    @PostMapping("/files")
    public ResponseEntity<FileResponseDTO> postFile(@RequestBody FileRequestDTO fileRequestDTO) {
        log.debug("[postFile][START][fileRequestDTO: {}]", fileRequestDTO.toString());
        return ResponseEntity.ok(blobFileService.addFileToContainer(fileRequestDTO.getContainerName(),
                fileRequestDTO.getFilePathAndName(), fileRequestDTO.getFileContent()));
    }



}
