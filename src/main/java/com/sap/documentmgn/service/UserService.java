package com.sap.documentmgn.service;

import com.sap.documentmgn.dto.UserDTO;
import com.sap.documentmgn.dto.UserRegistrationDTO;
import com.sap.documentmgn.entity.Document;
import com.sap.documentmgn.entity.ROLES;
import com.sap.documentmgn.entity.User;
import com.sap.documentmgn.mapper.UserMapper;
import com.sap.documentmgn.repository.DocumentRepository;
import com.sap.documentmgn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService{
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getUsers() {
        log.info("Fetching all users from the database");

        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toUserDTO).collect(Collectors.toList());
    }

    public List<UserDTO> getUsersByTen(int offset) {
        log.info("Fetching 10 users from the database");

        Pageable pageable = PageRequest.of(offset, 10);
        Page<User> users = userRepository.findAll(pageable);
        return users.stream().map(userMapper::toUserDTO).collect(Collectors.toList());
    }

    public void setRole(Long userId, List<ROLES> roles, String adminUsername) {
        Optional<User> adminOpt = userRepository.findByUsername(adminUsername);
        User admin = adminOpt.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found!"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("User with id {} not found", userId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
                });

        if (user.getId().equals(admin.getId())) {
            log.warn("Admin {} attempted to change their own role", adminUsername);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot change your own role");
        }

        user.setRoles(roles);
        userRepository.save(user);
        log.info("Admin {} set roles {} for user {}", adminUsername, roles, user.getUsername());
    }

    public void deleteUser(Long userId, String initUsername) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("User with id {} not found", userId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
                });

        User initUser = userRepository.findByUsername(initUsername)
                .orElseThrow(() -> {
                    log.warn("User with id {} not found", initUsername);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
                });

        if (user.getUsername().equals(initUsername) && user.getRoles().contains(ROLES.ADMIN)) {
            log.warn("Admin {} attempted to delete their own account", initUsername);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete your admin account");
        }

        if (!user.getUsername().equals((initUsername)) && !initUser.getRoles().contains(ROLES.ADMIN)) {
            log.warn("{} attempted to delete another account", initUsername);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only an admin account can delete other accounts");
        }

        userRepository.delete(user);
        log.info("{} deleted user {}", initUsername, user.getUsername());
    }

    public void deleteDocument(Long documentId, String username) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> {
                    log.warn("Document with id {} not found", documentId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found");
                });

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
                });
        if (!document.getAuthor().getUsername().equals(username) && !user.getRoles().contains(ROLES.ADMIN)) {
            log.warn("User with username {} attempted to delete document with id {} created by {}", username, documentId, document.getAuthor().getUsername());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only delete documents you created");
        }

        documentRepository.delete(document);
        log.info("Document with id {} deleted", documentId);
    }

    public UserDTO registerUser(UserRegistrationDTO registrationDTO) {
        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        user.addRole(ROLES.READER);

        User savedUser = userRepository.save(user);

        return userMapper.toUserDTO(savedUser);
    }

    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return userMapper.toUserDTO(user);
    }
}
