package com.sap.documentmgn.controller;

import com.sap.documentmgn.dto.DocumentDTO;
import com.sap.documentmgn.entity.Document;
import com.sap.documentmgn.service.DocumentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/documents")
public class DocumentController {

    private final  DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping
    public List<DocumentDTO> getDocuments() {
        return documentService.getDocuments();
    }
}