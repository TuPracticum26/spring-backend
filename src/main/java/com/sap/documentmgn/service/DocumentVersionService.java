package com.sap.documentmgn.service;

import com.sap.documentmgn.dto.DocumentVersionDTO;
import com.sap.documentmgn.entity.DocumentVersion;
import com.sap.documentmgn.repository.DocumentVersionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DocumentVersionService {
    private final DocumentVersionRepository documentVersionRepository;

    public DocumentVersionService(DocumentVersionRepository documentVersionRepository) {
        this.documentVersionRepository = documentVersionRepository;
    }

    public DocumentVersionDTO rejectVersion(Long documentId, Long versionId) {
        DocumentVersion version = documentVersionRepository.findById(versionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Version not found"));
        if (!version.getDocument().getId().equals(documentId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Version does not belong to this document");
        }
        if (!version.getStatus().equals("DRAFT")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only DRAFT versions can be rejected");
        }
        version.setStatus("REJECTED");

        documentVersionRepository.save(version);

        return new DocumentVersionDTO(version.getId(), version.getStatus());

    }
}
