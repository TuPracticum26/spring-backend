package com.sap.documentmgn.service;
import com.sap.documentmgn.dto.DocumentDTO;
import com.sap.documentmgn.dto.DocumentVersionDTO;
import com.sap.documentmgn.entity.Document;
import com.sap.documentmgn.entity.DocumentVersion;
import com.sap.documentmgn.mapper.DocumentMapper;
import com.sap.documentmgn.repository.DocumentRepository;
import com.sap.documentmgn.repository.DocumentVersionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    private final DocumentVersionRepository documentVersionRepository;

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
//        DocumentVersion latestVersion = documentVersionRepository.findTopByDocumentIdOrderByVersionNumberDesc(document.getId())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Document has no versions"));
        return dto;
    }
}