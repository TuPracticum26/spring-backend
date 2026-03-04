package com.sap.documentmgn.entity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name="DOCUMENT")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Enumerated(EnumType.STRING)
    private DocumentStatus status;

    @Column(nullable=false)
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name="created_by", nullable=false)
    private Account createdBy;

    @OneToMany(mappedBy="document", cascade = CascadeType.ALL)
    private List<DocumentVersion> versions;

    public long getId(){
        return id;
    }
    public String getName(){
        return name;
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
    public List<DocumentVersion> getVersions(){
        return versions;
    }
    public void setName(String name){
        this.name=name;
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
    public void setVersions(List<DocumentVersion> versions){
        this.versions= versions;
    }

//    //много документи - 1 автор
//    @ManyToOne
//    //събира в полето created_by id-то на създателя и може чрез методи да се дотъпи името му
//    @JoinColumn(name = "created_by", nullable = false)
//    //това е референция към обекта от тип Account, за да можем да му използваме методите
//    private Account createdBy;
//
//    //1 документ - много версии
//    //връзката се управлява от полето document в класа DocumentVersion
//    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
//    private List<DocumentVersion> versions;

}
