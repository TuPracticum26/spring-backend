package com.sap.documentmgn.entity;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="DOCUMENT_VERSION")
public class DocumentVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable=false)
    private String versionNumber;

    @Column(nullable=false)
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name="created_by", nullable=false)
    private Account createdBy;

    @ManyToOne
    @JoinColumn(name="document_id", nullable = false)
    private Document document;

    @Enumerated(EnumType.STRING)
    private DocumentStatus status;

    private String content;
    private String comments;

    public long getId(){
        return id;
    }
    public String getVersionNumber(){
        return versionNumber;
    }
    public DocumentStatus getStatus(){
        return status;
    }
    public LocalDate getCreatedAt(){
        return createdAt;
    }
    public Account getCreatedBy(){
        return createdBy;
    }
    public Document getDocument(){
        return document;
    }
    public void setVersionNumber(String versionNumber){
        this.versionNumber=versionNumber;
    }
    public void setStatus(DocumentStatus status){
        this.status=status;
    }
    public void setCreatedAt( LocalDate createdAt){
        this.createdAt=createdAt;
    }
    public void setCreatedBy(Account createdBy){
        this.createdBy= createdBy;
    }
    public String getContent(){
        return content;
    }
    public void setDocument(Document document){
        this.document= document;
    }
    public void setContent(String content){
        this.content=content;
    }
    public String getComments(){
        return comments;
    }
    public void setComments(String comments){
        this.comments=comments;
    }
}
