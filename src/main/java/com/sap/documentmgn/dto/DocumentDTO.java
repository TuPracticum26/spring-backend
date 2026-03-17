package com.sap.documentmgn.dto;
import java.time.LocalDateTime;

public class DocumentDTO {
    private Long id;
    private String title;
    private String authorUsername;
    private String content;
    private LocalDateTime creationDate;

    public DocumentDTO(Long id, String title, String authorUsername, String content, LocalDateTime creationDate) {
        this.id = id;
        this.title = title;
        this.authorUsername = authorUsername;
        this.content = content;
        this.creationDate = creationDate;
    }

    public DocumentDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}