package com.sap.documentmgn.service;

import com.sap.documentmgn.dto.DocumentVersionDTO;
import com.sap.documentmgn.dto.UserDTO;
import com.sap.documentmgn.dto.UserRegistrationDTO;
import com.sap.documentmgn.entity.DocumentVersion;
import com.sap.documentmgn.entity.ROLES;
import com.sap.documentmgn.entity.User;
import com.sap.documentmgn.mapper.UserMapper;
import com.sap.documentmgn.repository.DocumentRepository;
import com.sap.documentmgn.repository.DocumentVersionRepository;
import com.sap.documentmgn.repository.UserRepository;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final DocumentVersionRepository documentVersionRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getUsers() {
        log.info("Fetching all users from the database");

        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toUserDTO).collect(Collectors.toList());
    }

    @Transactional
    public void setRole(Long userId, List<ROLES> roles, String adminUsername) {
        User admin = userRepository.findByUsername(adminUsername)
                .orElseThrow(() -> {
                    log.warn("Admin with username {} not found", adminUsername);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found!");
                });

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("User with id {} not found", userId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
                });
        if (!admin.getRoles().contains(ROLES.ADMIN)) {
            log.warn("User {} attempted to set role without admin privileges", adminUsername);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only an admin can set roles");
        }

        if (user.getId().equals(admin.getId())) {
            log.warn("Admin {} attempted to change their own role", adminUsername);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot change your own role");
        }

        user.setRoles(roles);
        userRepository.save(user);
        log.info("Admin {} set role {} for user {}", adminUsername, roles, user.getUsername());
    }

    public List<UserDTO> getUsersByTen(int offset) {
        log.info("Fetching 10 users from the database");

        Pageable pageable = PageRequest.of(offset, 10);
        Page<User> users = userRepository.findAll(pageable);
        return users.stream().map(userMapper::toUserDTO).collect(Collectors.toList());
    }

    public void deleteUser(Long userId, String initUsername) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("User with id {} not found", userId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
                });

        User initUser = userRepository.findByUsername(initUsername)
                .orElseThrow(() -> {
                    log.warn("User with username {} not found", initUsername);
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

    @Transactional
    public UserDTO registerUser(UserRegistrationDTO registrationDTO) {
        Optional<User> optUser = userRepository.findByUsername(registrationDTO.getUsername());
        optUser.ifPresent(u-> {
                    log.warn("User with username {} already exists", registrationDTO.getUsername());
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
                });

        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        user.addRole(ROLES.READER);
        User savedUser = userRepository.save(user);

        log.info("Registered new user with username {}", registrationDTO.getUsername());
        return userMapper.toUserDTO(savedUser);
    }

    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return userMapper.toUserDTO(user);
    }

    public List<DocumentVersionDTO> getAllUserVersions(Long userId) {
        List<DocumentVersion> userDocVersions = documentVersionRepository.findDocumentVersionsByUser(userId);
        return userDocVersions.stream()
                .map(dv -> new DocumentVersionDTO(
                        dv.getId(),
                        dv.getStatus(),
                        dv.getVersionNumber(),
                        dv.getTitle(),
                        dv.getContent(),
                        dv.getCreatedBy().getUsername(),
                        dv.getCreatedAt(),
                        dv.getDocument().getId(),
                        dv.getComments()
                ))
                .toList();
    }

    public List<DocumentVersionDTO> getAllUserVersionsPage(Long userId, int offset) {
        Pageable pageable = PageRequest.of(offset, 10);
        Page<DocumentVersion> userDocVersions = documentVersionRepository.findDocumentVersionsByUserPage(userId, pageable);
        return userDocVersions.stream()
                .map(dv -> new DocumentVersionDTO(
                        dv.getId(),
                        dv.getStatus(),
                        dv.getVersionNumber(),
                        dv.getTitle(),
                        dv.getContent(),
                        dv.getCreatedBy().getUsername(),
                        dv.getCreatedAt(),
                        dv.getDocument().getId(),
                        dv.getComments()
                ))
                .toList();
    }

    public List<DocumentVersionDTO> getAllTeamVersionsPage(int offset) {
        Pageable pageable = PageRequest.of(offset, 10);
        Page<DocumentVersion> userDocVersions = documentVersionRepository.findAll(pageable);
        return userDocVersions.stream()
                .map(dv -> new DocumentVersionDTO(
                        dv.getId(),
                        dv.getStatus(),
                        dv.getVersionNumber(),
                        dv.getTitle(),
                        dv.getContent(),
                        dv.getCreatedBy().getUsername(),
                        dv.getCreatedAt(),
                        dv.getDocument().getId(),
                        dv.getComments()
                ))
                .toList();
    }

    public List<DocumentVersionDTO> getAllPendingTeamVersionsPage(@Min(0) int offset) {
        Pageable pageable = PageRequest.of(offset, 10);
        Page<DocumentVersion> userDocVersions = documentVersionRepository.findAllPendingPage(pageable);
        return userDocVersions.stream()
                .map(dv -> new DocumentVersionDTO(
                        dv.getId(),
                        dv.getStatus(),
                        dv.getVersionNumber(),
                        dv.getTitle(),
                        dv.getContent(),
                        dv.getCreatedBy().getUsername(),
                        dv.getCreatedAt(),
                        dv.getDocument().getId(),
                        dv.getComments()
                ))
                .toList();
    }
}
