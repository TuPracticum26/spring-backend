package com.sap.documentmgn.controller;

import com.sap.documentmgn.dto.DocumentHistoryDTO;
import com.sap.documentmgn.dto.DocumentHistorySummaryDTO;
import com.sap.documentmgn.dto.DocumentVersionDTO;
import com.sap.documentmgn.service.DocumentVersionService;
import jakarta.validation.constraints.Min;
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

    public ResponseEntity<?> DocumentApprove(@PathVariable @Min(1) Long docId, @PathVariable Long versionNumber, Principal principal){
        if(docId == null || docId <= 0 || versionNumber == null || versionNumber <= 0){
            return ResponseEntity.badRequest().body("Invalid document id or version number");
        }
        String username = principal.getName();
        if(username == null || username.isEmpty()){
            return ResponseEntity.status(401).body("User not authenticated");
        }
        documentVersionService.approveVersion(docId, versionNumber, username);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('REVIEWER')")
    @PostMapping("/{docId}/versions/{versionNumber}/reject")
    public DocumentVersionDTO rejectVersion(@PathVariable @Min(1) Long docId, @PathVariable Long versionNumber) {
        return documentVersionService.rejectVersion(docId, versionNumber);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'AUTHOR', 'REVIEWER')")
    @GetMapping("/{docId}/versions/{verId}/comments")

    public List<String> postComment(@PathVariable @Min(1) Long docId, @PathVariable @Min(1) Long verId) {
        return documentVersionService.getComments(docId, verId);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'AUTHOR', 'REVIEWER')")
    @PostMapping("/{docId}/versions/{verId}/comments")

    public ResponseEntity<?> postComment(@PathVariable @Min(1) Long docId, @PathVariable @Min(1) Long verId, @RequestBody String comment) {
        if(comment == null || comment.trim().isEmpty()){
            return ResponseEntity.badRequest().body("Comment cannot be empty");
        }
        if(comment.length() > 1000){
            return ResponseEntity.badRequest().body("Comment too long (max 1000 characters)");
        }
        documentVersionService.postComment(docId, verId, comment);
        return ResponseEntity.ok().build();
    }

    //Взема версия по номер
    @PreAuthorize("hasAnyRole('ADMIN', 'AUTHOR', 'REVIEWER')")
    @GetMapping("/{docId}/versions/num/{versionNumber}")
    public ResponseEntity<DocumentVersionDTO> getDocumentVersionByNumber(@PathVariable Long docId, @PathVariable Integer versionNumber){
        DocumentVersionDTO version = documentVersionService.getSpecificVersion(docId, versionNumber);
        return ResponseEntity.ok(version);
    }
}
