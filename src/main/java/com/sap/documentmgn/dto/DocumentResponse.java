package com.sap.documentmgn.dto;

import java.time.LocalDateTime;
import java.util.List;

public class DocumentResponse {
    private Long id;
    private String title;
    private String authorUsername;
    private LocalDateTime createdAt;
    private List<DocumentVersionResponse> versions;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthorUsername() { return authorUsername; }
    public void setAuthorUsername(String authorUsername) { this.authorUsername = authorUsername; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public List<DocumentVersionResponse> getVersions() { return versions; }
    public void setVersions(List<DocumentVersionResponse> versions) { this.versions = versions; }
}