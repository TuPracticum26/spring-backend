package com.sap.documentmgn.service;

import com.sap.documentmgn.dto.RegisterRequest;
import com.sap.documentmgn.dto.UserResponse;
import com.sap.documentmgn.entity.User;
import com.sap.documentmgn.enums.Role;
import com.sap.documentmgn.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public UserResponse register(RegisterRequest request) {
        User user = new User(
                request.getUsername(),
                request.getPassword(),
                Role.READER
        );
        User savedUser = userRepository.save(user);
        return new UserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getRole()
        );
    }
    public UserResponse updateRole(Long userID,Role newRole){
        User user = userRepository.findById(userID).orElseThrow(() -> new RuntimeException("User not found with id: "+userID));
        user.setRole(newRole);
        User updatedUser = userRepository.save(user);
        return new UserResponse(
                updatedUser.getId(),
                updatedUser.getUsername(),
                updatedUser.getRole()
        );
    }
}
