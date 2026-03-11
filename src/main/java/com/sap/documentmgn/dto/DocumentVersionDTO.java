package com.sap.documentmgn.dto;

import java.time.LocalDateTime;

public class DocumentVersionDTO {
    private Long id;
    private Integer versionNumber;
    private String status;
    private LocalDateTime createdAt;

    public DocumentVersionDTO(Long id,
                              Integer versionNumber,
                              String status,
                              LocalDateTime createdAt) {
        this.id = id;
        this.versionNumber = versionNumber;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getVersionNumber() {
        return versionNumber;
    }
    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
