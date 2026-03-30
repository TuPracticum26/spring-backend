package com.sap.documentmgn.dto;

import com.sap.documentmgn.entity.Document;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DocumentVersionDTO {
    private Long id;
    private String status;

    private Integer versionNumber;
    private String content;
    private String createdByUsername;
    private LocalDateTime createdAt;
    private Long documentId;

    public DocumentVersionDTO(Long id, String status){
        this.id = id;
        this.status = status;
    }

    public DocumentVersionDTO(Long id, Integer versionNumber, String content, String status, String createdByUsername, LocalDateTime createdAt, Long documentId) {
        this.id = id;
        this.versionNumber = versionNumber;
        this.content = content;
        this.status = status;
        this.createdByUsername = createdByUsername;
        this.createdAt = createdAt;
        this.documentId = documentId;
    }
}
