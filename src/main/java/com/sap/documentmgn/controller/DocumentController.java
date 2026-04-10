package com.sap.documentmgn.controller;

import com.sap.documentmgn.dto.DocumentDTO;
import com.sap.documentmgn.dto.DocumentHistoryDTO;
import com.sap.documentmgn.dto.DocumentHistorySummaryDTO;
import com.sap.documentmgn.dto.DocumentVersionDTO;
import com.sap.documentmgn.service.DocumentService;
import com.sap.documentmgn.service.DocumentVersionService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("/api/v1/documents")
public class DocumentController {
    private final DocumentService documentService;
    private final DocumentVersionService documentVersionService;

    public DocumentController(DocumentService documentService, DocumentVersionService documentVersionService) {
        this.documentService = documentService;
        this.documentVersionService = documentVersionService;
    }

    @GetMapping
    public List<DocumentDTO> getDocuments() {
        return documentService.getDocuments();
    }


    @GetMapping("/{docId}")
    public DocumentDTO getDocument(@PathVariable Long docId) {
        return documentService.getDocumentById(docId);
    }

    @PreAuthorize("hasRole('AUTHOR')")
    @PostMapping
    public ResponseEntity<DocumentDTO> createDocument(@Valid @RequestBody DocumentDTO documentDTO, Principal principal) {
        String username = principal.getName();
        DocumentDTO createdDocument = documentService.createDocument(documentDTO, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDocument);
    }

}
