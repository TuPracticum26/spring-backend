package com.sap.documentmgn.controller;

import com.sap.documentmgn.dto.DocumentVersionDTO;
import com.sap.documentmgn.dto.UserDTO;
import com.sap.documentmgn.entity.ROLES;
import com.sap.documentmgn.service.DocumentService;
import com.sap.documentmgn.service.UserService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    private final DocumentService documentService;

    public UserController(UserService userService, DocumentService documentService) {
        this.userService = userService;
        this.documentService = documentService;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/api/v1/users")
    public List<UserDTO> getUsers(){
        return userService.getUsers();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/api/v1/users/{page}")
    public List<UserDTO> getUsersByTen(@PathVariable @Min(0) int page){
        return userService.getUsersByTen(page);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'AUTHOR', 'REVIEWER')")
    @GetMapping("/api/v1/users/versions/{page}")
    public List<DocumentVersionDTO> getAllTeamVersionsPage(@PathVariable @Min(0) int page) {
        return userService.getAllTeamVersionsPage(page);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'AUTHOR', 'REVIEWER')")
    @GetMapping("/api/v1/users/versions/pending/{page}")
    public List<DocumentVersionDTO> getAllPendingTeamVersionsPage(@PathVariable @Min(0) int page) {
        return userService.getAllPendingTeamVersionsPage(page);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'AUTHOR', 'REVIEWER')")
    @GetMapping("/api/v1/users/{userId}/versions")
    public List<DocumentVersionDTO> getAllUserVersions(@PathVariable @Min(1) Long userId) {
        return userService.getAllUserVersions(userId);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'AUTHOR', 'REVIEWER')")
    @GetMapping("/api/v1/users/{userId}/versions/{page}")
    public List<DocumentVersionDTO> getAllUserVersionsPage(@PathVariable @Min(1) Long userId, @PathVariable @Min(0) int page) {
        return userService.getAllUserVersionsPage(userId, page);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/v1/admin/setRole/{userId}")
    public ResponseEntity<?> setRole(@PathVariable @Min(1) Long userId, @RequestBody List<ROLES> roles, @NonNull Principal principal) {
        String adminUsername = principal.getName();
        userService.setRole(userId, roles, adminUsername);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/v1/admin/deleteUser/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable @Min(1) Long userId, @NotNull Principal principal) {
        String initUsername = principal.getName();
        userService.deleteUser(userId, initUsername);
        return ResponseEntity.ok().build();
    }
}
