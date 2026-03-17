package com.sap.documentmgn.service;
import com.sap.documentmgn.dto.DocumentDTO;
import com.sap.documentmgn.entity.Document;
import com.sap.documentmgn.entity.DocumentVersion;
import com.sap.documentmgn.entity.User;
import com.sap.documentmgn.repository.DocumentRepository;
import com.sap.documentmgn.repository.DocumentVersionRepository;
import com.sap.documentmgn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {
    @Autowired
    private final DocumentRepository documentRepository;
    @Autowired
    private DocumentVersionRepository documentVersionRepository;
    @Autowired
    private UserRepository userRepository;

    public DocumentService(DocumentRepository documentRepository, DocumentVersionRepository documentVersionRepository, UserRepository userRepository) {
        this.documentRepository = documentRepository;
        this.documentVersionRepository = documentVersionRepository;
        this.userRepository = userRepository;
    }


    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public List<DocumentDTO> getDocuments() {
        List<Document> documents = documentRepository.findAll();
        List<DocumentDTO> listOfDocuments = new ArrayList<>();
        for (Document doc : documents) {
            listOfDocuments.add(
                    new DocumentDTO(
                            doc.getId(),
                            doc.getTitle()));

        }
        return listOfDocuments;
    }

    public void approveVersion(Long docId, Long versionNumber, String username) {
        Document document = documentRepository.findById(docId).orElseThrow(() -> new RuntimeException("404 e not found"));

        DocumentVersion version = documentVersionRepository.findById(versionNumber).orElseThrow(() -> new RuntimeException("404 e not found"));

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("401 (unauthenticated)");
        }
        if (!version.getDocument().getId().equals(document.getId())) {
            throw new RuntimeException("400 (bad request)");
        }
        if (!user.getRole().equalsIgnoreCase("admin") && !user.getRole().equalsIgnoreCase("reviewer")) {
            throw new RuntimeException("403 (forbidden)");
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
        return new DocumentDTO(document.getId(), document.getTitle(), document.getAuthor().getUsername(), latestVersion.getContent(), document.getCreatedAt());
    }
}