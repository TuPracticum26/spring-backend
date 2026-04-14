package com.sap.documentmgn.controller;

import com.sap.documentmgn.dto.DocumentHistoryDTO;
import com.sap.documentmgn.dto.DocumentHistorySummaryDTO;
import com.sap.documentmgn.service.DocumentVersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/documents/{docId}/history")
public class DocumentHistoryController {

    private final DocumentVersionService documentVersionService;

    @PreAuthorize("hasAnyRole('ADMIN', 'AUTHOR', 'REVIEWER')")
    @GetMapping
    public ResponseEntity<DocumentHistoryDTO> getHistory(@PathVariable Long docId) {
        return ResponseEntity.ok(
                documentVersionService.getDocumentHistory(docId)
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'AUTHOR', 'REVIEWER')")
    @GetMapping("/summary")
    public ResponseEntity<DocumentHistorySummaryDTO> getHistorySummary(@PathVariable Long docId) {
        return ResponseEntity.ok(
                documentVersionService.getDocumentHistorySummary(docId)
        );
    }
}