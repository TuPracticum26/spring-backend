package com.sap.documentmgn.service;

import com.sap.documentmgn.dto.DocumentDTO;
import com.sap.documentmgn.entity.Document;
import com.sap.documentmgn.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentService {
    private  final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }


    public List<DocumentDTO> getDocuments() {
        List<Document> allFromDabase = documentRepository.findAll();
        List<DocumentDTO> documents = new ArrayList<>();
        for (Document document : allFromDabase) {
            DocumentDTO documentDTO = new DocumentDTO();
            documentDTO.setId(document.getId());
            documentDTO.setTitle(document.getTitle());
            documentDTO.setAuthorUsername(document.getAuthor().getUsername());
            documents.add(documentDTO);
        }
        return documents;
    }
}
