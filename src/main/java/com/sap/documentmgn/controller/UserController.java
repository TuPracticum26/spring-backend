package com.sap.documentmgn.controller;

import com.sap.documentmgn.dto.DocumentVersionDTO;
import com.sap.documentmgn.dto.UserDTO;
import com.sap.documentmgn.entity.ROLES;
import com.sap.documentmgn.service.UserService;
import jakarta.validation.constraints.Min;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@Validated
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/api/v1/users")
    public List<UserDTO> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/api/v1/users/{page}")
    public List<UserDTO> getUsersByTen(@PathVariable int page){
        return userService.getUsersByTen(page);
    }

    @GetMapping("/api/v1/users/{userId}/versions")
    public List<DocumentVersionDTO> getAllUserVersions(@PathVariable Long userId) {
        return userService.getAllUserVersions(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/v1/admin/setRole/{userId}")
    public ResponseEntity<?> setRole(@PathVariable @Min(1) Long userId, @RequestBody List<ROLES> roles, @NonNull Principal principal) {
        String adminUsername = principal.getName();
        userService.setRole(userId, roles, adminUsername);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/v1/admin/deleteUser/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable @Min(1) Long userId, @NonNull Principal principal) {
        String initUsername = principal.getName();
        userService.deleteUser(userId, initUsername);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('AUTHOR')")
    @DeleteMapping("/api/v1/deleteDocument/{documentId}")
    public ResponseEntity<?> deleteDocument(@PathVariable @Min(1) Long documentId, @NonNull Principal principal) {
        String initUsername = principal.getName();
        userService.deleteDocument(documentId, initUsername);
        return ResponseEntity.ok().build();
    }
}
