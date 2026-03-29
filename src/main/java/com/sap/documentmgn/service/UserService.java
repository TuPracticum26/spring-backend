package com.sap.documentmgn.service;
import com.sap.documentmgn.dto.UserDTO;
import com.sap.documentmgn.entity.Document;
import com.sap.documentmgn.entity.User;
import com.sap.documentmgn.mapper.UserMapper;
import com.sap.documentmgn.repository.DocumentRepository;
import com.sap.documentmgn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService{
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;
    private final UserMapper userMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toUserDTO).collect(Collectors.toList());
    }

    public void setRole(Long userId, String role, String adminUsername) {
        User admin = userRepository.findByUsername(adminUsername);

        if (!role.equalsIgnoreCase("admin") && !role.equalsIgnoreCase("author") && !role.equalsIgnoreCase("reviewer") && !role.equalsIgnoreCase("reader")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Role");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (user.getId().equals(admin.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot change your own role");
        }

        user.setRole(role);
        userRepository.save(user);
    }

    public void deleteUser(Long userId, String adminUsername) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (user.getUsername().equals(adminUsername)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete your admin account");
        }

        userRepository.delete(user);
    }

    public void deleteDocument(Long documentId) {

        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found"));

        documentRepository.delete(document);
    }
}
