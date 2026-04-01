package com.sap.documentmgn.mapper;

import com.sap.documentmgn.dto.DocumentVersionDTO;
import com.sap.documentmgn.entity.DocumentVersion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocumentVersionMapper {

    DocumentVersionDTO toDocumentVersionDTO(DocumentVersion documentVersion);
    DocumentVersion toEntity(DocumentVersionDTO documentVersionDTO);

    @Mapping(target = "createdByUsername", source = "createdBy.username")
    @Mapping(target = "content", ignore = true)
    DocumentVersionDTO toDocumentVersionSummaryDTO(DocumentVersion documentVersion);
}
