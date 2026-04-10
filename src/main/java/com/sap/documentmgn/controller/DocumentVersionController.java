package com.sap.documentmgn.controller;

import com.sap.documentmgn.dto.DocumentHistoryDTO;
import com.sap.documentmgn.dto.DocumentHistorySummaryDTO;
import com.sap.documentmgn.dto.DocumentVersionDTO;
import com.sap.documentmgn.service.DocumentVersionService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/documents")
public class DocumentVersionController {
    private final DocumentVersionService documentVersionService;

    public DocumentVersionController(DocumentVersionService documentVersionService) {
        this.documentVersionService = documentVersionService;
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('REVIEWER')")
    @PostMapping("{docId}/versions/{versionNumber}/approve")
    public ResponseEntity<?> DocumentApprove(@PathVariable Long docId, @PathVariable Long versionNumber, Principal principal){
        String username = principal.getName();
        documentVersionService.approveVersion(docId, versionNumber, username);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('REVIEWER')")
    @PostMapping("/{docId}/versions/{versionNumber}/reject")
    public DocumentVersionDTO rejectVersion(@PathVariable Long docId, @PathVariable Long versionNumber) {
        return documentVersionService.rejectVersion(docId, versionNumber);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'AUTHOR', 'REVIEWER')")
    @GetMapping("/{docId}/versions/{verId}/comments")
    public List<String> getComments(@PathVariable Long docId, @PathVariable Long verId) {
        return documentVersionService.getComments(docId, verId);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'AUTHOR', 'REVIEWER')")
    @PostMapping("/{docId}/versions/{verId}/comments")
    public ResponseEntity<?> postComment(@PathVariable Long docId, @PathVariable Long verId, @RequestBody String comment) {
        documentVersionService.postComment(docId, verId, comment);
        return ResponseEntity.ok().build();
    }

    //Взема версия по номер
    @PreAuthorize("hasAnyRole('ADMIN', 'AUTHOR', 'REVIEWER')")
    @GetMapping("/{docId}/versions/num/{versionNumber}")
    public ResponseEntity<DocumentVersionDTO> getDocumentVersionByNumber(@PathParam("docId") @PathVariable Long docId, @PathParam("versionNumber") @PathVariable Integer versionNumber){
        DocumentVersionDTO version = documentVersionService.getSpecificVersion(docId, versionNumber);
        return ResponseEntity.ok(version);
    }
}
