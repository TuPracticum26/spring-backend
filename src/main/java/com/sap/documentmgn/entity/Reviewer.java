package com.sap.documentmgn.entity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="REVIEWER")
public class Reviewer extends Account {
    @Column(name="egn", length=10,nullable=false, unique=true)
    private String EGN;

    private String lastApprovedDoc;
    private String lastRejectedDoc;

    public String getEGN(){
        return EGN;
    }
    public String getLastApprovedDoc(){
        return lastApprovedDoc;
    }
    public String getLastRejectedDoc(){
        return lastRejectedDoc;
    }
    public void setEGN(String EGN){
        this.EGN = EGN;
    }
    public void setLastApprovedDoc(String lastApprovedDoc){
        this.lastApprovedDoc = lastApprovedDoc;
    }
    public void setLastRejectedRodeDoc(String lastRejectedDoc){
        this.lastRejectedDoc = lastRejectedDoc;
    }
}
