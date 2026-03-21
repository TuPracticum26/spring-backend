package com.sap.documentmgn.service;
import com.sap.documentmgn.dto.UserDTO;
import com.sap.documentmgn.entity.User;
import com.sap.documentmgn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserDTO> getUsers(){
        List<User> users = userRepository.findAll();
        List<UserDTO> usersDTO = new ArrayList<>();

        for( User user: users){
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setRole(user.getRole());
            usersDTO.add(userDTO);
        }
        return usersDTO;
    }

}
