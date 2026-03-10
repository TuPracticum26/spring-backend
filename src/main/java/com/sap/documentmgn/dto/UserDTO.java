package com.sap.documentmgn.dto;

public class UserDTO {
    private Long id;
    private String username;
    private String role;

    public UserDTO(){}

    public long getID() {
        return id;
    }
    public String getUsername(){
        return username;
    }
    public String getRole(){
        return role;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setRole(String role){
        this.role=role;
    }
}
