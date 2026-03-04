package com.sap.documentmgn.entity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "AUTHOR")
public class Author extends Account {

    @Column(name="egn", length=10, nullable= false, unique = true)
    private String EGN;


    private Integer createdDocs;
    private Integer editedDocs;

    private LocalDate lastCreatedDoc;
    private LocalDate lastEditedDoc;

    public String getEGN(){
        return EGN;
    }
    public int getCreatedDocs(){
        return createdDocs;
    }
    public int getEditedDocs(){
        return editedDocs;
    }
    public LocalDate getLastCreatedDoc(){
        return lastCreatedDoc;
    }
    public LocalDate getLastEditedDoc(){
        return lastEditedDoc;
    }
    public void setEGN(String EGN){
        this.EGN = EGN;
    }
    public void setCreatedDocs(int createdDocs){
        this.createdDocs = createdDocs;
    }
    public void setEditedDocs(int editedDocs){
        this.editedDocs = editedDocs;
    }
    public void setLastCreatedDoc(LocalDate lastCreatedDoc){
        this.lastCreatedDoc = lastCreatedDoc;
    }
    public void setLastEditedDoc(LocalDate lastEditedDoc){
        this.lastEditedDoc = lastEditedDoc;
    }
}
