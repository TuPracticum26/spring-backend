package com.sap.documentmgn.entity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="READER")
public class Reader extends Account{
    @Column(name="egn", length=10, nullable= false, unique = true)
    private String EGN;

    private String lastRodeDoc;

    public String getEGN(){
        return EGN;
    }
    public String getLastRodeDoc() {
        return lastRodeDoc;
    }
    public void setEGN(String EGN){
        this.EGN = EGN;
    }
    public void setLastRodeDoc(String lastRodeDoc){
        this.lastRodeDoc = lastRodeDoc;
    }

}
