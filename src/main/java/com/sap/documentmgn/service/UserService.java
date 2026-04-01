package com.sap.documentmgn.service;
import com.sap.documentmgn.dto.UserDTO;
import com.sap.documentmgn.entity.Document;
import com.sap.documentmgn.entity.ROLES;
import com.sap.documentmgn.entity.User;
import com.sap.documentmgn.mapper.UserMapper;
import com.sap.documentmgn.repository.DocumentRepository;
import com.sap.documentmgn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService{
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;
    private final UserMapper userMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getUsers() {
        log.info("Fetching all users from the database");

        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toUserDTO).collect(Collectors.toList());
    }

    public void setRole(Long userId, ROLES role, String adminUsername) {
        User admin = userRepository.findByUsername(adminUsername);

        if (!role.equals(ROLES.ADMIN) && !role.equals(ROLES.AUTHOR) && !role.equals(ROLES.REVIEWER) && !role.equals(ROLES.READER)) {
            log.warn("Invalid role {} provided by admin {}", role, adminUsername);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Role");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("User with id {} not found", userId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
                });

        if (user.getId().equals(admin.getId())) {
            log.warn("Admin {} attempted to change their own role", adminUsername);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot change your own role");
        }

        user.addRole(role);
        userRepository.save(user);
        log.info("Admin {} set role {} for user {}", adminUsername, role, user.getUsername());
    }

    public void deleteUser(Long userId, String adminUsername) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("User with id {} not found", userId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
                });

        if (user.getUsername().equals(adminUsername)) {
            log.warn("Admin {} attempted to delete their own account", adminUsername);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete your admin account");
        }

        userRepository.delete(user);
        log.info("Admin {} deleted user {}", adminUsername, user.getUsername());
    }

    public void deleteDocument(Long documentId, String username) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> {
                    log.warn("Document with id {} not found", documentId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found");
                });
        if (!document.getAuthor().getUsername().equals(username)) {
            log.warn("User with username {} attempted to delete document with id {} created by {}", username, documentId, document.getAuthor().getUsername());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only delete documents you created");
        }

        documentRepository.delete(document);
        log.info("Document with id {} deleted", documentId);
    }
}
