package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileListResponseDTO {

    private Integer code;

    private String message;

    private String containerName;

    private int blobListSize;

    private List<String> blobList;

}
