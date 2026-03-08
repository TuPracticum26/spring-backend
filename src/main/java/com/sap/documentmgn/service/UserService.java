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
    public UserResponse register(RegisterRequest request){
        Role assignedRole;
        if(request.getRole() != null){
            assignedRole = request.getRole();
        } else {
            assignedRole = Role.READER;
        }
        User user = new User( request.getUsername(),request.getPassword(),assignedRole );
        User savedUser = userRepository.save(user);
        return new UserResponse(savedUser.getId(),savedUser.getUsername(),savedUser.getRole());
    }
}
