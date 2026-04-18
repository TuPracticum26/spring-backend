package com.sap.documentmgn.service;

import com.sap.documentmgn.dto.DocumentHistoryDTO;
import com.sap.documentmgn.dto.DocumentHistorySummaryDTO;
import com.sap.documentmgn.dto.DocumentVersionDTO;
import com.sap.documentmgn.dto.DocumentVersionSummaryDTO;
import com.sap.documentmgn.entity.*;
import com.sap.documentmgn.mapper.DocumentVersionMapper;
import com.sap.documentmgn.repository.DocumentRepository;
import com.sap.documentmgn.repository.DocumentVersionRepository;
import com.sap.documentmgn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentVersionService {
    private final DocumentVersionRepository documentVersionRepository;
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;
    private final DocumentVersionMapper documentVersionMapper;

    public void approveVersion(Long docId, Long versionNumber, String username) {
        DocumentVersion version = documentVersionRepository
                .findByDocumentIdAndVersionNumber(docId, versionNumber)
                .orElseThrow(() -> {
                    log.warn("Version number {} not found for document {}", versionNumber, docId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Version not found");
                });

        Optional<User> userOpt = userRepository.findByUsername(username);
        User user = userOpt.orElseThrow(() -> {
            log.warn("Username {} not found", username);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        });

        log.debug("User {} has roles {}", username, user.getRoles());

        if (version.getStatus() != VersionStatus.PENDING) {
            log.warn("Version number {} for document with id {} has status {} and cannot be approved", versionNumber, docId, version.getStatus());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only DRAFT versions can be approved");
        }
        version.setStatus(VersionStatus.APPROVED);
        version.updateEvent(user);

        documentVersionRepository.save(version);
        log.info("Version number {} for document with id {} approved by user {}", versionNumber, docId, username);
    }

    public DocumentVersionDTO rejectVersion(Long docId, Long versionNumber) {
        DocumentVersion version = documentVersionRepository
                .findByDocumentIdAndVersionNumber(docId,versionNumber)
                .orElseThrow(() -> {
                    log.warn("Version with id {} not found", versionNumber);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Version not found");
                });

        if (version.getStatus() != VersionStatus.PENDING) {
            log.warn("Version number {} has status {} and cannot be rejected", versionNumber, version.getStatus());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only DRAFT versions can be rejected");
        }
        version.setStatus(VersionStatus.REJECTED);

        documentVersionRepository.save(version);
        log.info("Version number {} for document with id {} rejected", versionNumber, docId);

        return documentVersionMapper.toDocumentVersionDTO(version);

    }

    public DocumentHistoryDTO getDocumentHistory(Long documentId){
        log.info("Fetching document history for document with id {}", documentId);

        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> {
                    log.warn("Document with id {} not found", documentId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found with id: " + documentId);
                });
        List<DocumentVersion> versions = documentVersionRepository
                .findVersionWithCreatorByDocumentId(documentId);
        log.debug("Found {} versions for document {}", versions.size(), documentId);

        List<DocumentVersionDTO> versionDTOs = versions.stream()
                .map(documentVersionMapper::toDocumentVersionDTO)
                .collect(Collectors.toList());
        return new DocumentHistoryDTO(
                document.getId(),
                document.getTitle(),
                versionDTOs,
                versionDTOs.size()
        );
    }

    public DocumentHistorySummaryDTO getDocumentHistorySummary(Long documentId){
        log.info("Fetching document history summary for document with id {}", documentId);

        Document document = documentRepository.findById(documentId)
                .orElseThrow(()-> {
                    log.warn("Document with id {} not found", documentId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found with id: " + documentId);
                });
        List<DocumentVersion> versions = documentVersionRepository
                .findByDocumentIdOrderByVersionNumberAsc(documentId);
        log.debug("Found {} versions for document {}", versions.size(), documentId);

        List<DocumentVersionSummaryDTO> versionDTOs = versions.stream()
                .map(documentVersionMapper::toSummaryDTO)
                .collect(Collectors.toList());
        return new DocumentHistorySummaryDTO(
                document.getId(),
                document.getTitle(),
                versionDTOs,
                versionDTOs.size()
        );
    }

    public DocumentVersionDTO getSpecificVersion(Long documentId, Integer versionNumber){
        log.info("Fetching version number {} for document with id {}", versionNumber, documentId);

        List<DocumentVersion> versions = documentVersionRepository
                .findByDocumentIdOrderByVersionNumberAsc(documentId);
        log.debug("Found {} versions for document {}", versions.size(), documentId);

        return versions.stream()
                .filter(v -> v.getVersionNumber().equals(versionNumber))
                .findFirst()
                .map(documentVersionMapper::toDocumentVersionDTO)
                .orElseThrow(() -> {
                    log.warn("Version number {} not found for document with id {}", versionNumber, documentId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Version " + versionNumber + " not found for document " + documentId);
                });
    }

    public  List<String> getComments(Long docId, Long verId) {
        DocumentVersion documentVersion = documentVersionRepository
                .findByDocumentIdAndVersionNumber(docId, verId)
                .orElseThrow(() -> {
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Document version not found with id: " + verId);
                });
        return documentVersion.getComments();
    }

    public void postComment(Long docId, Long verId, String comment) {
        DocumentVersion documentVersion = documentVersionRepository
                .findByDocumentIdAndVersionNumber(docId, verId)
                   .orElseThrow(() -> {
                       return new ResponseStatusException(HttpStatus.NOT_FOUND, "Document version not found with id: " + verId);
                   });

        comment = comment.trim();
        if (!comment.isEmpty()) {
            documentVersion.getComments().add(comment);
            documentVersionRepository.save(documentVersion);
        }
    }
    public DocumentVersionDTO getVersionDetails(Long docId, Long verId) {
        log.info("Fetching version details for document {} and version {}", docId, verId);

        DocumentVersion version = documentVersionRepository
                .findByDocumentIdAndVersionNumber(docId, verId)
                .orElseThrow(() -> {
                    log.warn("Version {} not found for document {}", verId, docId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Version not found for document " + docId);
                });

        return documentVersionMapper.toDocumentVersionDTO(version);
    }
}
