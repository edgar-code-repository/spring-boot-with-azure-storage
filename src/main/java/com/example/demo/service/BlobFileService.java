package com.example.demo.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.models.BlobStorageException;
import com.example.demo.dto.FileListResponseDTO;
import com.example.demo.dto.FileResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import java.io.ByteArrayInputStream;

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
                    .code(1).message("Success!!!").containerName(containerName)
                    .blobListSize(filesList.size()).blobList(filesList)
                    .build();
        }
        catch (BlobStorageException e) {
            log.debug("[getListOfFiles][BlobStorageException][Error listing files from container: {}]", e.toString());
            fileListResponse.setCode(2);
            fileListResponse.setMessage("Container does not exist.");
        }

        log.debug("[getListOfFiles][END][blob list size: {}]", filesList.size());
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
            log.debug("[getFileFromContainer][Error when retrieving file][{}]", e.toString());
        }

        log.debug("[getFileFromContainer][END][file exists???: {}]", fileResponse.isExists());
        return fileResponse;
    }

    public FileResponseDTO addFileToContainer(String containerName, String filePathAndName, String fileContent) {
        log.debug("[addFileToContainer][START][containerName: {}]", containerName);
        log.debug("[addFileToContainer][START][filePathAndName: {}]", filePathAndName);

        FileResponseDTO fileResponse = new FileResponseDTO();
        fileResponse.setContainerName(containerName);
        fileResponse.setFilePathAndName(filePathAndName);
        fileResponse.setContent(fileContent);

        try {
            BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);
            BlobClient blobClient = blobContainerClient.getBlobClient(filePathAndName);
            byte[] decodedBytes = Base64.getDecoder().decode(fileContent);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decodedBytes);
            blobClient.upload(byteArrayInputStream);

            fileResponse.setExists(true);
        }
        catch (Exception e) {
            log.debug("[addFileToContainer][ERROR][Error when uploading file: {}]", e.toString());
        }

        log.debug("[addFileToContainer][END][file exists???: {}]", fileResponse.isExists());
        return fileResponse;
    }

}
