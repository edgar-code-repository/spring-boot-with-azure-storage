package com.example.demo.service;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BlobFileService {

    @Autowired
    private BlobServiceClient blobServiceClient;

    public List<String> getListOfFiles(String containerName) {
        log.debug("[getListOfFiles][START][containerName: {}]", containerName);

        List<String> filesList = new ArrayList<>();
        try {
            BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);
            for (BlobItem blobItem: blobContainerClient.listBlobs()) {
                filesList.add(blobItem.getName());
            }
        }
        catch (Exception e) {
            log.debug("[getListOfFiles][ERROR][Message: {}]", e.getMessage());
        }

        log.debug("[getListOfFiles][END][file list size: {}]", filesList.size());
        return filesList;
    }

}
