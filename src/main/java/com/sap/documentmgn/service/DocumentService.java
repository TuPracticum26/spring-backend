package com.sap.documentmgn.service;

import com.sap.documentmgn.entity.Document;
import com.sap.documentmgn.entity.DocumentVersion;
import com.sap.documentmgn.entity.User;
import com.sap.documentmgn.repository.DocumentRepository;
import com.sap.documentmgn.repository.DocumentVersionRepository;
import com.sap.documentmgn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import java.time.LocalDateTime;

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private DocumentVersionRepository documentVersionRepository;
    @Autowired
    private UserRepository userRepository;

    public void approveVersion(Long docId, Long verId, Long userId) {
            Document document = documentRepository.findById(docId).orElseThrow(() -> new RuntimeException("document not found!"));

            DocumentVersion version = documentVersionRepository.findById(verId).orElseThrow(() -> new RuntimeException("version not found!"));

            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found!"));

            if (!version.getDocument().getId().equals(document.getId())){
                throw new RuntimeException("document version doesn't match!");
            }
            if (!user.getRole().equalsIgnoreCase("admin") && !user.getRole().equalsIgnoreCase("reviewer")){
                throw new RuntimeException("User not allowed to approve document");
            }

            version.setStatus("approved");
            version.setCreatedAt(LocalDateTime.now());
            version.setCreatedBy(user);

            documentVersionRepository.save(version);
    }
}
