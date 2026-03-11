package com.sap.documentmgn.controller;

import com.sap.documentmgn.dto.DocumentRequest;
import com.sap.documentmgn.dto.DocumentResponse;
import com.sap.documentmgn.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public ResponseEntity<DocumentResponse> createDocument(@RequestBody DocumentRequest request) {
        DocumentResponse createdDocument = documentService.createDocument(request);
        return ResponseEntity.ok(createdDocument);
    }
}