package com.sap.documentmgn.repository;

import com.sap.documentmgn.entity.DocumentVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentVersionRepository extends JpaRepository<DocumentVersion, Long> {
    List<DocumentVersion> findByDocumentIdOrderByVersionNumberAsc(Long documentId);
    Optional<DocumentVersion> findTopByDocumentIdOrderByVersionNumberDesc(Long documentId);

    @Query("SELECT dv FROM DocumentVersion dv " +
            "LEFT JOIN FETCH dv.createdBy " +
            "WHERE dv.document.id = :documentId " +
            "ORDER BY dv.versionNumber ASC")
    List<DocumentVersion> findVersionWithCreatorByDocumentId(@Param("documentId") Long documentId);
}