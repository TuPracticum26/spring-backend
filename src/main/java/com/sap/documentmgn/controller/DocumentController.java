package com.sap.documentmgn.controller;

import com.sap.documentmgn.dto.DocumentDTO;
import com.sap.documentmgn.service.DocumentService;
import jakarta.validation.constraints.Min;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import jakarta.validation.Valid;

import java.util.List;


@RestController
@RequestMapping("/api/v1/documents")
@Validated
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping
    public List<DocumentDTO> getDocuments() {
        return documentService.getDocuments();
    }


    @GetMapping("/{docId}")
    public DocumentDTO getDocument(@PathVariable @Min(1) Long docId) {
        return documentService.getDocumentById(docId);
    }

    @PreAuthorize("hasRole('AUTHOR')")
    @PostMapping
    public ResponseEntity<DocumentDTO> createDocument(@Valid @RequestBody DocumentDTO documentDTO,@NonNull Principal principal) {
        String username = principal.getName();
        DocumentDTO createdDocument = documentService.createDocument(documentDTO, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDocument);
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('AUTHOR')")
    @DeleteMapping("/api/v1/deleteDocument/{documentId}")
    public ResponseEntity<?> deleteDocument(@PathVariable @Min(1) Long documentId, @NonNull Principal principal) {
        String username = principal.getName();
        documentService.deleteDocument(documentId, username);
        return ResponseEntity.ok().build();
    }
}
