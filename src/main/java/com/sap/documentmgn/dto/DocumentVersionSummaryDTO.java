package com.sap.documentmgn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DocumentVersionSummaryDTO {
    private Long id;
    private String status;
    private Integer versionNumber;
    private String createdByUsername;
    private LocalDateTime createdAt;
}
