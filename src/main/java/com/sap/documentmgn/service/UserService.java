package com.sap.documentmgn.service;
import com.sap.documentmgn.dto.UserDTO;
import com.sap.documentmgn.entity.User;
import com.sap.documentmgn.mapper.UserMapper;
import com.sap.documentmgn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toUserDTO).collect(Collectors.toList());
    }

    public void setRole(Long userId, String role, String adminName) {
        User admin = userRepository.findByUsername(adminName);

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
}
