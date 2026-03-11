package com.sap.documentmgn.service;

import com.sap.documentmgn.entity.Document;
import com.sap.documentmgn.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public List<Document> getDocuments() {
        return documentRepository.findAll();
    }
}
