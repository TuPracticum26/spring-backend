package com.sap.documentmgn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DocumentHistoryDTO {
    private Long documentId;
    private String documentTitle;
    private List<DocumentVersionDTO> versions;
    private Integer totalVersions;
}
