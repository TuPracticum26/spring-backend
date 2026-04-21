package com.sap.documentmgn.service;

import com.sap.documentmgn.dto.DocumentDTO;
import com.sap.documentmgn.entity.Document;
import com.sap.documentmgn.entity.DocumentVersion;
import com.sap.documentmgn.entity.ROLES;
import com.sap.documentmgn.entity.User;
import com.sap.documentmgn.mapper.DocumentMapper;
import com.sap.documentmgn.repository.DocumentRepository;
import com.sap.documentmgn.repository.DocumentVersionRepository;
import com.sap.documentmgn.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.sap.documentmgn.entity.ROLES.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DocumentServiceUnitTests {
    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private DocumentMapper documentMapper;

    @Mock
    private DocumentVersionRepository documentVersionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DocumentService documentService;

    @Test
    public void testGetDocuments_success(){
        Document doc = new Document();
        doc.setTitle("Test Doc");

        DocumentDTO docDTO = new DocumentDTO();
        docDTO.setTitle("Test Doc");

        when(documentRepository.findAll()).thenReturn(List.of(doc));
        when(documentMapper.toDocumentDTO(doc)).thenReturn(docDTO);

        List<DocumentDTO> documents = documentService.getDocuments();
        assertEquals(1, documents.size());
        assertEquals("Test Doc", documents.getFirst().getTitle());

        verify(documentRepository).findAll();
        verify(documentMapper).toDocumentDTO(doc);
    }

    @Test
    public void testGetDocumentById_success(){
        Document doc = new Document();
        doc.setId(1L);

        DocumentDTO docDTO = new DocumentDTO();
        docDTO.setId(1L);

        DocumentVersion version = new DocumentVersion();
        version.setContent("Latest Content");

        when(documentRepository.findById(1L)).thenReturn(Optional.of(doc));
        when(documentMapper.toDocumentDTO(doc)).thenReturn(docDTO);
        when(documentVersionRepository.findTopByDocumentIdOrderByVersionNumberDesc(1L))
                .thenReturn(Optional.of(version));

        DocumentDTO result = documentService.getDocumentById(1L);
        assertEquals("Latest Content", result.getContent());

        verify(documentRepository).findById(1L);
        verify(documentVersionRepository).findTopByDocumentIdOrderByVersionNumberDesc(1L);
    }

    @Test
    public void testCreateDocument_success(){
        DocumentDTO dto = new DocumentDTO();
        dto.setTitle("New Document");
        dto.setContent("Initial Content");

        User author = new User();
        author.setUsername("testUser");

        Document savedDoc = new Document();
        savedDoc.setId(1L);

        DocumentDTO mappedDto = new DocumentDTO();
        mappedDto.setId(1L);

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(author));
        when(documentRepository.save(any(Document.class))).thenReturn(savedDoc);
        when(documentMapper.toDocumentDTO(savedDoc)).thenReturn(mappedDto);

        DocumentDTO result = documentService.createDocument(dto, "testUser");

        assertEquals("Initial Content", result.getContent());
        verify(userRepository).findByUsername("testUser");
        verify(documentRepository).save(any(Document.class));
        verify(documentVersionRepository).save(any(DocumentVersion.class));
    }

    @Test
    public void testCreateDocument_userNotFound() {
        DocumentDTO dto = new DocumentDTO();
        dto.setTitle("New Document");

        when(userRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> documentService.createDocument(dto, "unknownUser"));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        verify(documentRepository, never()).save(any(Document.class));
    }

    @Test
    public void testDeleteDocument_successAsAuthor() {
        User author = new User();
        author.setUsername("testUser");
        author.setRoles(List.of(READER));

        Document doc = new Document();
        doc.setId(1L);
        doc.setAuthor(author);

        when(documentRepository.findById(1L)).thenReturn(Optional.of(doc));
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(author));

        documentService.deleteDocument(1L, "testUser");

        verify(documentRepository).delete(doc);
    }

    @Test
    public void testDeleteDocument_successAsAdmin() {
        User author = new User();
        author.setUsername("authorUser");

        Document doc = new Document();
        doc.setId(1L);
        doc.setAuthor(author);

        User adminUser = new User();
        adminUser.setUsername("adminUser");
        adminUser.setRoles(List.of(ADMIN));

        when(documentRepository.findById(1L)).thenReturn(Optional.of(doc));
        when(userRepository.findByUsername("adminUser")).thenReturn(Optional.of(adminUser));

        documentService.deleteDocument(1L, "adminUser");

        verify(documentRepository).delete(doc);
    }

    @Test
    public void testDeleteDocument_forbidden() {
        User author = new User();
        author.setUsername("authorUser");

        Document doc = new Document();
        doc.setId(1L);
        doc.setAuthor(author);

        User regularUser = new User();
        regularUser.setUsername("regularUser");
        regularUser.setRoles(List.of(READER));

        when(documentRepository.findById(1L)).thenReturn(Optional.of(doc));
        when(userRepository.findByUsername("regularUser")).thenReturn(Optional.of(regularUser));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> documentService.deleteDocument(1L, "regularUser"));

        assertEquals(HttpStatus.FORBIDDEN, ex.getStatusCode());
        verify(documentRepository, never()).delete(any(Document.class));
    }

    @Test
    public void testDeleteDocument_documentNotFound() {
        when(documentRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> documentService.deleteDocument(1L, "testUser"));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }
}
