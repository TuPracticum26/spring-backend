package com.sap.documentmgn.service;

import com.sap.documentmgn.dto.UserDTO;
import com.sap.documentmgn.dto.UserRegistrationDTO;
import com.sap.documentmgn.entity.ROLES;
import com.sap.documentmgn.entity.User;
import com.sap.documentmgn.mapper.UserMapper;
import com.sap.documentmgn.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.sap.documentmgn.entity.ROLES.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTests {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void testGetAllUsers_success() {
        User user = new User();
        user.setUsername("testUser");

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testUser");

        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);

        List<UserDTO> users = userService.getUsers();
        assertEquals(1, users.size());
        assertEquals("testUser", users.getFirst().getUsername());

        verify(userRepository).findAll();
        verify(userMapper).toUserDTO(user);
    }


    @Test
    public void testSetRole_success(){
        User user = new User();
        user.setId(1L);

        User admin = new User();
        admin.setId(2L);
        admin.setRoles(List.of(ADMIN, REVIEWER));
        admin.setUsername("testAdmin");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findByUsername("testAdmin")).thenReturn(Optional.of(admin));

        userService.setRole(1L, List.of(READER), "testAdmin");
        assertTrue(user.getRoles().contains(READER));

        verify(userRepository).findById(1L);
        verify(userRepository).findByUsername("testAdmin");
        verify(userRepository).save(user);
    }

    @Test
    public void testSetRole_adminNotFound() {
        when(userRepository.findByUsername("testAdmin"))
                .thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> userService.setRole(1L, List.of(ADMIN), "testAdmin"));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    public void testSetRole_userNotFound() {
        when(userRepository.findByUsername("testAdmin")).thenReturn(Optional.of(new User()));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> userService.setRole(1L, List.of(READER), "testAdmin"));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    public void testSetRole_cannotChangeOwnRole() {
        User admin = new User();
        admin.setId(1L);
        admin.setUsername("testAdmin");

        when(userRepository.findByUsername("testAdmin"))
                .thenReturn(Optional.of(admin));

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(admin));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> userService.setRole(1L, List.of(READER), "testAdmin"));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }


    @Test
    public void testDeleteUser_success(){
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        User admin = new User();
        admin.setId(2L);
        admin.setUsername("testAdmin");
        admin.setRoles(List.of(ROLES.ADMIN));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findByUsername("testAdmin")).thenReturn(Optional.of(admin));
        userService.deleteUser(1L, "testAdmin");

        verify(userRepository).findById(1L);
        verify(userRepository).findByUsername("testAdmin");
        verify(userRepository).delete(user);
    }

    @Test
    public void testDeleteUser_userNotFound() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> userService.deleteUser(1L, "testAdmin"));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    public void testSetRole_cannotDeleteOwnAccount() {
        User admin = new User();
        admin.setId(1L);
        admin.setUsername("testAdmin");
        admin.setRoles(List.of(ROLES.ADMIN));

        when(userRepository.findById(1L)).thenReturn(Optional.of(admin));
        when(userRepository.findByUsername("testAdmin")).thenReturn(Optional.of(admin));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> userService.deleteUser(1L, "testAdmin"));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }


    @Test
    public void testRegisterUser_success() {
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setUsername("testUser");
        dto.setPassword("testUser123!");

        User savedUser = new User();
        savedUser.setUsername("testUser");

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testUser");

        when(passwordEncoder.encode("testUser123!")).thenReturn("encodedPass");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(userMapper.toUserDTO(savedUser)).thenReturn(userDTO);

        UserDTO result = userService.registerUser(dto);
        assertEquals("testUser", result.getUsername());

        verify(passwordEncoder).encode("testUser123!");
        verify(userRepository).save(any(User.class));
        verify(userMapper).toUserDTO(savedUser);
    }

    @Test
    public void testRegistrationUser_usernameTaken(){
        UserRegistrationDTO dto = new UserRegistrationDTO();
        dto.setUsername("testUser");

        UserRegistrationDTO dto2 = new UserRegistrationDTO();
        dto2.setUsername("testUser");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(new User()));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> userService.registerUser(dto));

        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
    }
}
