package com.sap.documentmgn.controller;

import com.sap.documentmgn.dto.DocumentDTO;
import com.sap.documentmgn.service.DocumentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
@Validated
public class DocumentController {

    private final DocumentService documentService;

    // Вземане на всички документи
    @GetMapping
    public ResponseEntity<List<DocumentDTO>> getDocuments() {
        return ResponseEntity.ok(documentService.getDocuments());
    }

    // Вземане на документ по ID
    @GetMapping("/{docId}")
    public ResponseEntity<DocumentDTO> getDocument(@PathVariable Long docId) {
        return ResponseEntity.ok(documentService.getDocumentById(docId));
    }

    // СЪЗДАВАНЕ НА ДОКУМЕНТ - Достъпно за автори
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_AUTHOR') or hasRole('AUTHOR')")
    public ResponseEntity<DocumentDTO> createDocument(
            @Valid @RequestBody DocumentDTO documentDTO,
            Principal principal) {

        String username = principal.getName();
        DocumentDTO createdDocument = documentService.createDocument(documentDTO, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDocument);
    }

    // ИЗТРИВАНЕ НА ДОКУМЕНТ - Достъпно за администратори и автори
    @DeleteMapping("/{documentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AUTHOR')")
    public ResponseEntity<Void> deleteDocument(
            @PathVariable @Min(1) Long documentId,
            Principal principal) {

        String username = principal.getName();
        documentService.deleteDocument(documentId, username);
        return ResponseEntity.noContent().build(); // Връщаме 204 No Content
    }
}