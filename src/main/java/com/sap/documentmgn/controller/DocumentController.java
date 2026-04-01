package com.sap.documentmgn.controller;

import com.sap.documentmgn.dto.DocumentDTO;
import com.sap.documentmgn.dto.DocumentHistoryDTO;
import com.sap.documentmgn.dto.DocumentVersionDTO;
import com.sap.documentmgn.service.DocumentService;
import com.sap.documentmgn.service.DocumentVersionService;
import jakarta.websocket.server.PathParam;
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


    //Взема пълна история на документ
    @GetMapping("/{docId}/history")
    public ResponseEntity<DocumentHistoryDTO> getDocumentHistory(@PathParam("docId") @PathVariable Long docId){
        DocumentHistoryDTO history = documentVersionService.getDocumentHistory(docId);
        return ResponseEntity.ok(history);
    }

    //Взема само summary на историята
    @GetMapping("/{docId}/history/summary")
    public ResponseEntity<DocumentHistoryDTO> getDocumentHistorySummary(@PathParam("docId") @PathVariable Long docId){
        DocumentHistoryDTO history = documentVersionService.getDocumentHistorySummary(docId);
        return ResponseEntity.ok(history);
    }

    //Взема версия по номер
    @GetMapping("/{docId}/versions/num/{versionNumber}")
    public ResponseEntity<DocumentVersionDTO> getDocumentVersionByNumber(@PathParam("docId") @PathVariable Long docId, @PathParam("versionNumber") @PathVariable Integer versionNumber){
        DocumentVersionDTO version = documentVersionService.getSpecificVersion(docId, versionNumber);
        return ResponseEntity.ok(version);
    }

}
