package com.sap.documentmgn.dto;
import com.sap.documentmgn.entity.DocumentVersion;
import com.sap.documentmgn.entity.User;
import java.util.List;

public class DocumentDTO {
    private Long id;
    private String title;
    private User author;
    private List<DocumentVersion> version;

    public DocumentDTO(Long id, String title, User author, List<DocumentVersion> version){
        this.id = id;
        this.title = title;
        this.author = author;
        this.version = version;
    }

    public long getID() {
        return id;
    }
    public String getTitle(){
        return title;
    }
    public User getAuthor(){
        return author;
    }
    public List<DocumentVersion> getVersion(){
        return version;
    }
}
