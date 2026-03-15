package com.sap.documentmgn.service;

import com.sap.documentmgn.dto.DocumentVersionDTO;
import com.sap.documentmgn.entity.DocumentVersion;
import com.sap.documentmgn.repository.DocumentVersionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DocumentVersionService {

    private final DocumentVersionRepository documentVersionRepository;

    public DocumentVersionService(DocumentVersionRepository documentVersionRepository) {
        this.documentVersionRepository = documentVersionRepository;
    }

    /**
     * Връща последната версия на документ по ID на документа.
     */
    public Optional<DocumentVersionDTO> getLatestVersion(Long documentId) {
        List<DocumentVersion> versions = documentVersionRepository.findByDocumentIdOrderByVersionNumberAsc(documentId);
        if (versions.isEmpty()) {
            return Optional.empty();
        }
        // вземаме последната версия в списъка (най-голям versionNumber)
        DocumentVersion latest = versions.get(versions.size() - 1);
        return Optional.of(DocumentVersionDTO.fromEntity(latest));
    }
}