package com.sap.documentmgn.dto;

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

    public DocumentVersionDTO(Long id, String status) {
        this.id = id;
        this.status = status;
    }

    public DocumentVersionDTO() {
    }
}