package com.example.demo.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobItem;
import com.example.demo.dto.FileListResponseDTO;
import com.example.demo.dto.FileResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@Slf4j
public class BlobFileService {

    @Autowired
    private BlobServiceClient blobServiceClient;

    public FileListResponseDTO getListOfFiles(String containerName) {
        log.debug("[getListOfFiles][START][containerName: {}]", containerName);

        List<String> filesList = new ArrayList<>();
        FileListResponseDTO fileListResponse = FileListResponseDTO.builder().containerName(containerName).build();
        try {
            BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);
            for (BlobItem blobItem: blobContainerClient.listBlobs()) {
                filesList.add(blobItem.getName());
            }

            fileListResponse = FileListResponseDTO.builder()
                    .containerName(containerName)
                    .fileListSize(filesList.size())
                    .fileList(filesList)
                    .build();
        }
        catch (Exception e) {
            log.debug("[getListOfFiles][ERROR][Message: {}]", e.getMessage());
        }

        log.debug("[getListOfFiles][END][file list size: {}]", filesList.size());
        return fileListResponse;
    }

    public FileResponseDTO getFileFromContainer(String containerName, String filePathAndName) {
        log.debug("[getFileFromContainer][START][containerName: {}]", containerName);
        log.debug("[getFileFromContainer][START][filePathAndName: {}]", filePathAndName);

        FileResponseDTO fileResponse = new FileResponseDTO();
        fileResponse.setContainerName(containerName);
        fileResponse.setFilePathAndName(filePathAndName);
        fileResponse.setContent("");

        try {
            BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);
            BlobClient blobClient = blobContainerClient.getBlobClient(filePathAndName);
            if (blobClient.exists()) {
                byte[] fileContent = blobClient.downloadContent().toBytes();
                String fileContentAsBase64 = Base64.getEncoder().encodeToString(fileContent);

                fileResponse.setExists(true);
                fileResponse.setContent(fileContentAsBase64);
            }
        }
        catch (Exception e) {
            log.debug("[getFileFromContainer][ERROR][Message: {}]", e.getMessage());
        }

        log.debug("[getFileFromContainer][END][file exists???: {}]", fileResponse.isExists());
        return fileResponse;
    }

}
