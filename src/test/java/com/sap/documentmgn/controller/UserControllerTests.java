package com.sap.documentmgn.controller;

import com.sap.documentmgn.entity.User;
import com.sap.documentmgn.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Collections.singletonList;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("tests")
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        User user = new User();
        user.setUsername("gosho");
        user.setPassword(passwordEncoder.encode("password"));
        user.setRole(singletonList("ADMIN"));
        userRepository.save(user);
    }

    @Test
    public void testGetUsers() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .with(httpBasic("gosho", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("gosho"));
    }

}

