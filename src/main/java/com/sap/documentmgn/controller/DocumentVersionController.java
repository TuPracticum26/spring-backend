package com.sap.documentmgn.controller;

import com.sap.documentmgn.dto.DocumentVersionDTO;
import com.sap.documentmgn.service.DocumentVersionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/document-versions")
public class DocumentVersionController {

    private final DocumentVersionService documentVersionService;

    public DocumentVersionController(DocumentVersionService documentVersionService) {
        this.documentVersionService = documentVersionService;
    }

    /**
     * GET /api/document-versions/latest/{documentId}
     * Връща последната версия на документа като DTO.
     */
    @GetMapping("/latest/{documentId}")
    public ResponseEntity<DocumentVersionDTO> getLatestVersion(@PathVariable Long documentId) {
        return documentVersionService.getLatestVersion(documentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}