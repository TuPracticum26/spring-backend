package com.sap.documentmgn.controller;

import com.sap.documentmgn.dto.UserDTO;
import com.sap.documentmgn.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/v1/users")
    public List<UserDTO> getUsers(Principal principal){
        return userService.getUsers();
    }
}
