package com.sap.documentmgn.controller;

import com.sap.documentmgn.dto.DocumentVersionDTO;
import com.sap.documentmgn.service.DocumentService;
import com.sap.documentmgn.service.DocumentVersionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/documents")
public class DocumentVersionController {
    private final DocumentService documentService;
    private final DocumentVersionService documentVersionService;

    public DocumentVersionController(DocumentVersionService documentVersionService, DocumentService documentService) {
        this.documentVersionService = documentVersionService;
        this.documentService = documentService;
    }

    @PostMapping("{docId}/versions/{versionNumber}/approve")
    public ResponseEntity DocumentApprove(@PathVariable Long docId, @PathVariable Long versionNumber, Principal principal){
        String username = principal.getName();
        documentVersionService.approveVersion(docId, versionNumber, username);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{docId}/versions/{verId}/reject")
    public DocumentVersionDTO rejectVersion(@PathVariable Long docId, @PathVariable Long verId) {
        return documentVersionService.rejectVersion(docId, verId);
    }

    @GetMapping("/{docId}/versions/{verId}/comments")
    public List<String> postComment(@PathVariable Long docId, @PathVariable Long verId) {
        return documentVersionService.getComments(docId, verId);
    }

    @PostMapping("/{docId}/versions/{verId}/comments")
    public ResponseEntity<?> postComment(@PathVariable Long docId, @PathVariable Long verId, @RequestBody String comment) {
        documentVersionService.postComment(docId, verId, comment);
        return ResponseEntity.ok().build();
    }
}
