package com.sap.documentmgn.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "document_versions")
public class DocumentVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;

    @NotNull
    private Integer versionNumber;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private VersionStatus status = VersionStatus.DRAFT;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @NotNull
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ElementCollection
    @CollectionTable(name = "version_comments", joinColumns = @JoinColumn(name = "version_id"))
    @Column(name = "comment", columnDefinition = "TEXT")
    private List<String> comments = new ArrayList<>();

    public void updateEvent(User user) {
        this.createdBy = user;
        this.createdAt = LocalDateTime.now();
    }
}