package com.sap.documentmgn.service;
import com.sap.documentmgn.dto.UserDTO;
import com.sap.documentmgn.entity.User;
import com.sap.documentmgn.mapper.UserMapper;
import com.sap.documentmgn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toUserDTO).collect(Collectors.toList());
    }
}
