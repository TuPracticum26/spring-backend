package com.sap.documentmgn.entity;
import jakarta.persistence.*;

@Entity
@Table(name="ADMIN")
public class Admin extends Account{

    @Column(name="egn", length=10, nullable = false, unique= true)
    private String EGN;

    // всеки админ има ключ за потвърждение, с него ще може да упражлява потребителите и ролите!!!
    @Column(name="access_key", length=7, nullable = false, unique = true)
    private String accessKey;

    public String getEGN(){
        return EGN;
    }
    public String getAccessKey(){
        return accessKey;
    }
    public void setEGN(String EGN){
        this.EGN = EGN;
    }
    public void setAccessKey(String accessKey){
        this.accessKey = accessKey;
    }

}
