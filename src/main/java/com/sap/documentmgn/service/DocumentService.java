package com.sap.documentmgn.service;

import com.sap.documentmgn.dto.DocumentRequest;
import com.sap.documentmgn.dto.DocumentResponse;
import com.sap.documentmgn.dto.DocumentVersionResponse;
import com.sap.documentmgn.entity.Document;
import com.sap.documentmgn.entity.DocumentVersion;
import com.sap.documentmgn.entity.User;
import com.sap.documentmgn.repository.DocumentRepository;
import com.sap.documentmgn.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    public DocumentService(DocumentRepository documentRepository, UserRepository userRepository) {
        this.documentRepository = documentRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public DocumentResponse createDocument(DocumentRequest request) {
        User author = userRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!"AUTHOR".equalsIgnoreCase(author.getRole())) {
            throw new RuntimeException("User does not have permission to create documents");
        }

        Document document = new Document();
        document.setTitle(request.getTitle());
        document.setAuthor(author);

        DocumentVersion firstVersion = new DocumentVersion();
        firstVersion.setDocument(document);
        firstVersion.setVersionNumber(1);
        firstVersion.setContent(request.getContent());
        firstVersion.setStatus("DRAFT");
        firstVersion.setCreatedBy(author);

        document.setVersions(List.of(firstVersion));

        Document savedDocument = documentRepository.save(document);
        return mapToResponse(savedDocument);
    }

    private DocumentResponse mapToResponse(Document document) {
        DocumentResponse response = new DocumentResponse();
        response.setId(document.getId());
        response.setTitle(document.getTitle());
        response.setAuthorUsername(document.getAuthor().getUsername());
        response.setCreatedAt(document.getCreatedAt());

        List<DocumentVersionResponse> versionResponses = document.getVersions().stream().map(v -> {
            DocumentVersionResponse vr = new DocumentVersionResponse();
            vr.setId(v.getId());
            vr.setVersionNumber(v.getVersionNumber());
            vr.setContent(v.getContent());
            vr.setStatus(v.getStatus());
            vr.setCreatedAt(v.getCreatedAt());
            return vr;
        }).collect(Collectors.toList());

        response.setVersions(versionResponses);
        return response;
    }
}