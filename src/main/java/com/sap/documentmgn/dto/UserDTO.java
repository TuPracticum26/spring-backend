package com.sap.documentmgn.dto;

import com.sap.documentmgn.entity.ROLES;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private List<ROLES> role;
        public UserDTO() {

        }
}
