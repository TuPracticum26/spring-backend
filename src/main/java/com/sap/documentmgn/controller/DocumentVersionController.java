package com.sap.documentmgn.controller;

import com.sap.documentmgn.dto.CommentDTO;
import com.sap.documentmgn.dto.DocumentVersionDTO;
import com.sap.documentmgn.dto.UserRegistrationDTO;
import com.sap.documentmgn.service.DocumentVersionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/documents")
@Validated
public class DocumentVersionController {
    private final DocumentVersionService documentVersionService;

    public DocumentVersionController(DocumentVersionService documentVersionService) {
        this.documentVersionService = documentVersionService;
    }

    @GetMapping("/{docId}/versions/{verId}")
    public ResponseEntity<DocumentVersionDTO> getDocumentVersionDetails(
        @PathVariable @Min(1) Long docId,
        @PathVariable @Min(1) Long verId){
        DocumentVersionDTO versionDetails = documentVersionService.getVersionDetails(docId,verId);
        return ResponseEntity.ok(versionDetails);
    }

    @PreAuthorize("hasAnyRole('ADMIN','AUTHOR','REVIEWER')")
    @PostMapping("/{docId}/versions/{verId}")
    public ResponseEntity<?> postDocumentVersion(@PathVariable @Min(1) Long docId, @PathVariable @Min(1) Integer verId, @Valid @RequestBody DocumentVersionDTO versionDTO, @NotNull Principal principal){
        String username = principal.getName();
        documentVersionService.createVersion(docId, verId, versionDTO, username);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('REVIEWER')")
    @PostMapping("{docId}/versions/{versionNumber}/approve")
    public ResponseEntity<?> documentApprove(@PathVariable @Min(1) Long docId, @PathVariable @Min(1) Long versionNumber,@NotNull Principal principal){
        String username = principal.getName();
        documentVersionService.approveVersion(docId, versionNumber, username);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('REVIEWER')")
    @PostMapping("/{docId}/versions/{versionNumber}/reject")
    public DocumentVersionDTO rejectVersion(@PathVariable @Min(1) Long docId, @PathVariable @Min(1) Long versionNumber) {
        return documentVersionService.rejectVersion(docId, versionNumber);
    }

    @GetMapping("/{docId}/versions/{verId}/comments")
    public List<String> getComment(@PathVariable @Min(1) Long docId, @PathVariable @Min(1) Long verId) {
        return documentVersionService.getComments(docId, verId);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'AUTHOR', 'REVIEWER')")
    @PostMapping("/{docId}/versions/{verId}/comments")
    public ResponseEntity<CommentDTO> postComment(@PathVariable @Min(1) Long docId, @PathVariable @Min(1) Long verId, @Valid @RequestBody CommentDTO comment) {
        documentVersionService.postComment(docId, verId, comment.getComment());
        return ResponseEntity.ok(comment);
    }

    //Взема версия по номер
    @GetMapping("/{docId}/versions/num/{versionNumber}")
    public ResponseEntity<DocumentVersionDTO> getDocumentVersionByNumber(@PathVariable @Min(1) Long docId, @PathVariable @Min(1) Integer versionNumber){
        DocumentVersionDTO version = documentVersionService.getSpecificVersion(docId, versionNumber);
        return ResponseEntity.ok(version);
    }
}
