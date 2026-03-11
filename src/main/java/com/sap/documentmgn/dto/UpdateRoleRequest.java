package com.sap.documentmgn.dto;

import com.sap.documentmgn.enums.Role;

public class UpdateRoleRequest {
    private Role role;

    public UpdateRoleRequest(){ }

    public UpdateRoleRequest(Role role){
        this.role = role;
    }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
