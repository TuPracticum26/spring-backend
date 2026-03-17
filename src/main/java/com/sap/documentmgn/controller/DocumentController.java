package com.sap.documentmgn.controller;

import com.sap.documentmgn.service.DocumentService;
import com.sap.documentmgn.dto.DocumentDTO;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/documents")
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/{docId}")
    public DocumentDTO getDocument(@PathVariable Long docId) {
        return documentService.getDocumentById(docId);
    }


}
