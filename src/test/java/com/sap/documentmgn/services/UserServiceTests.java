package com.sap.documentmgn.services;

import com.sap.documentmgn.dto.UserDTO;
import com.sap.documentmgn.entity.User;
import com.sap.documentmgn.repository.UserRepository;
import com.sap.documentmgn.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void getUsers_returnsUsersFromDatabase() {
        UserDTO expectedOutput = new UserDTO();
        expectedOutput.setId(1L);
        expectedOutput.setUsername("testuser");
        expectedOutput.setRole(List.of("USER"));

        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setRole(List.of("USER"));

        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        List<UserDTO> actualsUsers = userService.getUsers();
        Assertions.assertEquals(1, actualsUsers.size());
        UserDTO actualUser = actualsUsers.get(0);
        Assertions.assertEquals(expectedOutput.getId(), actualUser.getId());
        Assertions.assertEquals(expectedOutput.getUsername(), actualUser.getUsername());
    }
}
