package com.sap.documentmgn.controller;

import com.sap.documentmgn.dto.DocumentDTO;
import com.sap.documentmgn.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("/api/v1/documents")
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping
    public List<DocumentDTO> getDocuments() {
        return documentService.getDocuments();
    }


    @PostMapping("{docId}/versions/{versionNumber}/approve")
    public ResponseEntity DocumentApprove(@PathVariable Long docId, @PathVariable Long versionNumber, Principal principal){
        String username = principal.getName();
        documentService.approveVersion(docId, versionNumber, username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{docId}")
    public DocumentDTO getDocument(@PathVariable Long docId) {
        return documentService.getDocumentById(docId);
    }

}
