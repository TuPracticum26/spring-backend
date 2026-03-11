package com.sap.documentmgn.service;

import com.sap.documentmgn.dto.DocumentVersionDTO;
import com.sap.documentmgn.entity.DocumentVersion;
import com.sap.documentmgn.repository.DocumentVersionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {
    private final DocumentVersionRepository documentVersionRepository;

    public DocumentService(DocumentVersionRepository documentVersionRepository) {
        this.documentVersionRepository = documentVersionRepository;
    }

    public List<DocumentVersionDTO> getDocumentHistory(Long documentId) {
        List<DocumentVersion> versions =
                documentVersionRepository.findByDocumentIdOrderByVersionNumberAsc(documentId);
        return versions.stream()
                .map(version -> new DocumentVersionDTO(
                        version.getId(),
                        version.getVersionNumber(),
                        version.getStatus(),
                        version.getCreatedAt()
                ))
                .toList();
    }
}
