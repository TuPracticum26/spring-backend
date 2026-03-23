package com.sap.documentmgn.mapper;

import com.sap.documentmgn.dto.DocumentDTO;
import com.sap.documentmgn.entity.Document;
import com.sap.documentmgn.entity.DocumentVersion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocumentMapper {
    DocumentDTO toDocumentDTO(Document document);
    DocumentDTO toDocumentDTO(Document document, DocumentVersion version);
    Document toEntity(DocumentDTO documentDTO);
}
