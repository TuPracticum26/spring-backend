package com.sap.documentmgn.dto;
import com.sap.documentmgn.entity.DocumentVersion;
import com.sap.documentmgn.entity.User;
import java.util.List;

public class DocumentDTO {
    private Long id;
    private String title;

    public DocumentDTO(Long id, String title){
        this.id = id;
        this.title = title;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
}
