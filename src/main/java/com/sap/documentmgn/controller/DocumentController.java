package com.sap.documentmgn.controller;

import com.sap.documentmgn.dto.DocumentVersionDTO;
import com.sap.documentmgn.entity.DocumentVersion;
import com.sap.documentmgn.service.DocumentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/{id}/history")
    public List<DocumentVersionDTO> getDocumentHistory(@PathVariable Long id) {
        return documentService.getDocumentHistory(id);
    }
}
