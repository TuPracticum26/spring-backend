package com.sap.documentmgn.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "document_versions")
public class DocumentVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;

    @NotBlank
    private Integer versionNumber;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private VersionStatus status = VersionStatus.DRAFT;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @NotBlank
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public void updateEvent(User user) {
        this.createdBy = user;
        this.createdAt = LocalDateTime.now();
    }
}