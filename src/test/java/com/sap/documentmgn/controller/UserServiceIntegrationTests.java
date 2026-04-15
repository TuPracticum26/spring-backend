package com.sap.documentmgn.service;

import com.sap.documentmgn.entity.User;
import com.sap.documentmgn.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AllArgsConstructor
public class UserServiceIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
//    @MockitoBean
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp(){
        userRepository.deleteAll();
        User user = new User();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("Admin123!"));
        user.addRole(com.sap.documentmgn.entity.ROLES.ADMIN);
        userRepository.save(user);
    }
    @AfterEach
    void tearDown(){
        userRepository.deleteAll();
    }

    @Test
    public void getUsersShouldGetAllUsers() throws Exception{
        mockMvc.perform(get("/api/v1/users")
                    .with(httpBasic("admin","Admin123!")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
