package com.sap.documentmgn.service;
import com.sap.documentmgn.dto.DocumentDTO;
import com.sap.documentmgn.entity.*;
import com.sap.documentmgn.mapper.DocumentMapper;
import com.sap.documentmgn.repository.DocumentRepository;
import com.sap.documentmgn.repository.DocumentVersionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.sap.documentmgn.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    private final DocumentVersionRepository documentVersionRepository;
    private final UserRepository userRepository;

    public List<DocumentDTO> getDocuments() {
        List<Document> documents = documentRepository.findAll();
        log.debug("Fetched {} documents from the database", documents.size());
        return documents.stream().map(documentMapper::toDocumentDTO).collect(Collectors.toList());
    }

    public DocumentDTO getDocumentById(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Document with id {} not found", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found");
                });
        DocumentDTO dto = documentMapper.toDocumentDTO(document);
        DocumentVersion latestVersion = documentVersionRepository
                .findTopByDocumentIdOrderByVersionNumberDesc(id)
                .orElseThrow(() -> {
                    log.warn("Latest version of document with id {} not found", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "No version found");
                });
        dto.setContent(latestVersion.getContent());
        return dto;
    }
    @Transactional
    public DocumentDTO createDocument(DocumentDTO documentDTO, String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        User author = userOpt.orElseThrow(() -> {
                    log.warn("Author with username {} not found", username);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
                });
        Document document = new Document();
        document.setTitle(documentDTO.getTitle());
        document.setAuthor(author);
        document = documentRepository.save(document);
        DocumentVersion firstVersion = new DocumentVersion();
        firstVersion.setDocument(document);
        firstVersion.setVersionNumber(1);
        firstVersion.setContent(documentDTO.getContent());
        firstVersion.setStatus(VersionStatus.DRAFT);
        firstVersion.updateEvent(author);
        documentVersionRepository.save(firstVersion);
        log.info("Created new document with id {} and initial version 1 by author {}", document.getId(), username);
        DocumentDTO createdDto = documentMapper.toDocumentDTO(document);
        createdDto.setContent(firstVersion.getContent());
        return createdDto;
    }

    public void deleteDocument(Long documentId, String username) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> {
                    log.warn("Document with id {} not found", documentId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found");
                });

        Optional<User> userOpt = userRepository.findByUsername(username);
        User user = userOpt.orElseThrow(() -> {
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        });
        if (!document.getAuthor().getUsername().equals(username) && !user.getRoles().contains(ROLES.ADMIN)) {
            log.warn("User with username {} attempted to delete document with id {} created by {}", username, documentId, document.getAuthor().getUsername());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only delete documents you created");
        }

        documentRepository.delete(document);
        log.info("Document with id {} deleted", documentId);
    }
}