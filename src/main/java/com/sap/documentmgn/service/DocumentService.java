package com.sap.documentmgn.service;
import com.sap.documentmgn.dto.DocumentDTO;
import com.sap.documentmgn.entity.Document;
import com.sap.documentmgn.entity.DocumentVersion;
import com.sap.documentmgn.entity.User;
import com.sap.documentmgn.mapper.DocumentMapper;
import com.sap.documentmgn.repository.DocumentRepository;
import com.sap.documentmgn.repository.DocumentVersionRepository;
import com.sap.documentmgn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentVersionRepository documentVersionRepository;
    private final UserRepository userRepository;
    private final DocumentMapper documentMapper;

    public List<DocumentDTO> getDocuments() {
        List<Document> documents = documentRepository.findAll();
        return documents.stream().map(documentMapper::toDocumentDTO).collect(Collectors.toList());
    }


    public void approveVersion(Long docId, Long versionNumber, String username) {
        Document document = documentRepository.findById(docId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found"));

        DocumentVersion version = documentVersionRepository.findById(versionNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Version not found"));

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }
        if (!version.getDocument().getId().equals(document.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Version does not belong to the specified document");
        }
        if (!user.getRole().equalsIgnoreCase("admin") && !user.getRole().equalsIgnoreCase("reviewer")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have permission to approve versions");
        }

        version.setStatus("approved");
        version.updateEvent(user);

        documentVersionRepository.save(version);
    }


    public DocumentDTO getDocumentById(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found"));
        DocumentVersion latestVersion = documentVersionRepository.findTopByDocumentIdOrderByVersionNumberDesc(document.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Document has no versions"));
        return documentMapper.toDocumentDTO(document, latestVersion);
    }
}