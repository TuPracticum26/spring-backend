package com.sap.documentmgn.mapper;

import com.sap.documentmgn.dto.DocumentDTO;
import com.sap.documentmgn.entity.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocumentMapper {
    @Mapping(source = "author.username", target = "authorUsername")
    @Mapping(source = "createdAt", target = "creationDate")
    @Mapping(target = "content", ignore = true)
    DocumentDTO toDocumentDTO(Document document);

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "versions", ignore = true)
    @Mapping(source = "creationDate", target = "createdAt")
    Document toEntity(DocumentDTO documentDTO);
}
