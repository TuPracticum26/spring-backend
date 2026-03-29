package com.sap.documentmgn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private List<String> role;
        public UserDTO() {

        }
}
