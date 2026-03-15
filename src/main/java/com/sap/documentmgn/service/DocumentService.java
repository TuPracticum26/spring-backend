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

    public void approveVersion(Long docId, Long versionNumber, String username) {
            Document document = documentRepository.findById(docId).orElseThrow(() -> new RuntimeException("404 e not found"));

            DocumentVersion version = documentVersionRepository.findById(versionNumber).orElseThrow(() -> new RuntimeException("404 e not found"));

            User user = userRepository.findByUsername(username);
            if (user == null){
                throw new RuntimeException("401 (unauthenticated)");
            }
            if (!version.getDocument().getId().equals(document.getId())){
                throw new RuntimeException("400 (bad request)");
            }
            if (!user.getRole().equalsIgnoreCase("admin") && !user.getRole().equalsIgnoreCase("reviewer")){
                throw new RuntimeException("403 (forbidden)");
            }

            version.setStatus("approved");
            version.updateEvent(user);

            documentVersionRepository.save(version);
    }
}
