package com.sap.documentmgn.controller;

import com.sap.documentmgn.entity.Document;
import com.sap.documentmgn.entity.User;
import com.sap.documentmgn.repository.DocumentRepository;
import com.sap.documentmgn.service.DocumentService;
import jakarta.websocket.server.PathParam;
import org.apache.catalina.connector.Response;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


@RequestMapping("/api/v1/documents")
public class DocumentController {
    private final DocumentService documentService;
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("{docId}/versions/{versionNumber}/approve")
    public ResponseEntity DocumentApprove(@PathVariable Long docId, @PathVariable Long versionNumber, Principal principal){
        try {

            String username = principal.getName();
            documentService.approveVersion(docId, versionNumber, username);
            return ResponseEntity.ok().build();
        }
        catch(RuntimeException e){
            String message = e.getMessage();
            if(message.contains("404")){
                return ResponseEntity.notFound().build();
            }
            else if(message.contains("401")){
                return ResponseEntity.status(401).build();
            }
            else if(message.contains("403")){
                return ResponseEntity.status(403).build();
            }
            else if(message.contains("400")){
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.internalServerError().build();
        }
    }
}
