package com.sap.documentmgn.service;

import com.sap.documentmgn.entity.Document;
import com.sap.documentmgn.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import com.sap.documentmgn.dto.DocumentDTO;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public List<DocumentDTO> getDocuments() {
        List<Document> documents = documentRepository.findAll();
        List<DocumentDTO> listOfDocuments = new ArrayList<>();
        for (Document doc : documents) {
            DocumentDTO dto = new DocumentDTO(doc.getId(), doc.getTitle(), doc.getAuthor(), doc.getVersions());
            listOfDocuments.add(dto);
        }
        return listOfDocuments;
    }
}
