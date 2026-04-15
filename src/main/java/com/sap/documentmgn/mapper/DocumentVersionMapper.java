package com.sap.documentmgn.mapper;

import com.sap.documentmgn.dto.DocumentVersionDTO;
import com.sap.documentmgn.dto.DocumentVersionSummaryDTO;
import com.sap.documentmgn.entity.DocumentVersion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocumentVersionMapper {

    @Mapping(source = "createdBy.username", target = "createdByUsername")
    @Mapping(source = "document.id", target = "documentId")
    @Mapping(source = "status", target = "status")
    DocumentVersionDTO toDocumentVersionDTO(DocumentVersion documentVersion);

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "document", ignore = true)
    @Mapping(target = "status", source = "status")
    DocumentVersion toEntity(DocumentVersionDTO documentVersionDTO);

    @Mapping(source = "createdBy.username", target = "createdByUsername")
    @Mapping(source = "document.id", target = "id")
    @Mapping(source = "status", target = "status")
    DocumentVersionSummaryDTO toSummaryDTO(DocumentVersion documentVersion);

}
