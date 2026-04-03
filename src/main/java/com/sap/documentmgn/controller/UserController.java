package com.sap.documentmgn.controller;

import com.sap.documentmgn.dto.UserDTO;
import com.sap.documentmgn.entity.ROLES;
import com.sap.documentmgn.service.UserService;
import org.jspecify.annotations.NonNull;
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

    @GetMapping("/api/v1/users")
    public List<UserDTO> getUsers(){
        return userService.getUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/v1/admin/setRole/{userId}/{role}")
    public ResponseEntity<?> setRole(@PathVariable Long userId, @PathVariable ROLES role, @NonNull Principal principal) {
        String adminUsername = principal.getName();
        userService.setRole(userId, role, adminUsername);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/v1/admin/deleteUser/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId, @NonNull Principal principal) {
        String adminUsername = principal.getName();
        userService.deleteUser(userId, adminUsername);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('AUTHOR')")
    @DeleteMapping("/api/v1/deleteDocument/{documentId}")
    public ResponseEntity<?> deleteDocument(@PathVariable Long documentId, @NonNull Principal principal) {
        String username = principal.getName();
        userService.deleteDocument(documentId, username);
        return ResponseEntity.ok().build();
    }
}
