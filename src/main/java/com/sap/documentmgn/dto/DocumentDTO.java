package com.sap.documentmgn.dto;
import com.sap.documentmgn.entity.DocumentVersion;
import com.sap.documentmgn.entity.User;
import java.util.List;

public class DocumentDTO {
    private Long id;
    private String title;
    private String author;
    private List<DocumentVersion> versions;

    public DocumentDTO(Long id, String title, String author, List<DocumentVersion> version){
        this.id = id;
        this.title = title;
        this.author = author;
        this.versions = version;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthorUsername() { return author; }
    public List<DocumentVersion> getVersions() { return versions; }
}
