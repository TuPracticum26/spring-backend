package com.sap.documentmgn.dto;

import java.util.List;

public class UserDTO {
    private Long id;
    private String username;
    private List<String> role;

    public UserDTO(){}

    public long getID() {
        return id;
    }
    public String getUsername(){
        return username;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }
}
