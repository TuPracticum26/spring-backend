package com.sap.documentmgn.service;

import com.sap.documentmgn.dto.DocumentHistoryDTO;
import com.sap.documentmgn.dto.DocumentVersionDTO;
import com.sap.documentmgn.entity.Document;
import com.sap.documentmgn.entity.DocumentVersion;
import com.sap.documentmgn.repository.DocumentRepository;
import com.sap.documentmgn.repository.DocumentVersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentVersionService {
    private final DocumentVersionRepository documentVersionRepository;
    private final DocumentRepository documentRepository;

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

    public DocumentHistoryDTO getDocumentHistory(Long documentId){
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found with id: " + documentId));
        List<DocumentVersion> versions = documentVersionRepository
                .findVersionWithCreatorByDocumentId(documentId);
        List<DocumentVersionDTO> versionDTOs = versions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new DocumentHistoryDTO(
                document.getId(),
                document.getTitle(),
                versionDTOs,
                versionDTOs.size()
        );
    }

    public DocumentHistoryDTO getDocumentHistorySummary(Long documentId){
        Document document = documentRepository.findById(documentId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found with id: " + documentId));
        List<DocumentVersion> versions = documentVersionRepository
                .findByDocumentIdOrderByVersionNumberAsc(documentId);
        List<DocumentVersionDTO> versionDTOs = versions.stream()
                .map(version -> {
                    DocumentVersionDTO dto = convertToDTO(version);
                    dto.setContent(null);
                    return dto;
                })
                .collect(Collectors.toList());
        return new DocumentHistoryDTO(
                document.getId(),
                document.getTitle(),
                versionDTOs,
                versionDTOs.size()
        );
    }

    public DocumentVersionDTO getSpecificVersion(Long documentId, Integer versionNumber){
        List<DocumentVersion> versions = documentVersionRepository
                .findByDocumentIdOrderByVersionNumberAsc(documentId);
        return versions.stream()
                .filter(v -> v.getVersionNumber().equals(versionNumber))
                .findFirst()
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Version " + versionNumber + " not found for document " + documentId));
    }

    private DocumentVersionDTO convertToDTO(DocumentVersion version) {
        String createdByUsername = version.getCreatedBy() != null ?
                version.getCreatedBy().getUsername() : null;

        return new DocumentVersionDTO(
                version.getId(),
                version.getVersionNumber(),
                version.getContent(),
                version.getStatus(),
                createdByUsername,
                version.getCreatedAt(),
                version.getDocument().getId()
        );
    }
}
