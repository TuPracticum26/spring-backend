package com.sap.documentmgn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DocumentHistorySummaryDTO {
    private Long documentId;
    private String documentTitle;
    private List<DocumentVersionSummaryDTO> versions;
    private Integer totalVersions;
}
