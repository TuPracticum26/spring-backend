package com.sap.documentmgn.dto;

import com.sap.documentmgn.entity.Document;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DocumentVersionDTO {
    private Long id;
    private String status;

}
