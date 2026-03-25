package com.sap.documentmgn.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DocumentDTO {
    private Long id;
    private String title;
    private String authorUsername;
    private String content;
    private LocalDateTime creationDate;
}