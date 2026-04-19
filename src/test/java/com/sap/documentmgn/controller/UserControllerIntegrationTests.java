package com.sap.documentmgn.controller;

import com.sap.documentmgn.entity.ROLES;
import com.sap.documentmgn.entity.User;
import com.sap.documentmgn.repository.UserRepository;
import com.sap.documentmgn.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User savedUser;
    private User target;

    @BeforeEach
    void setUp(){
        userRepository.deleteAll();
        User user = new User();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("Admin123!"));
        user.setRoles(List.of(ROLES.ADMIN));
        savedUser = userRepository.save(user);

        User user2 = new User();
        user2.setUsername("user");
        user2.setPassword(passwordEncoder.encode("User123!"));
        user2.setRoles(List.of(ROLES.READER));
        target = userRepository.save(user2);
    }
    @AfterEach
    void tearDown(){
        userRepository.deleteAll();
    }

    @Test
    public void getUsers() throws Exception{
        mockMvc.perform(get("/api/v1/users")
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].username").value("admin"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getUsersByTen() throws Exception{
        mockMvc.perform(get("/api/v1/users/0")
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").isNumber())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getAllTeamVersionsPage() throws Exception {
        mockMvc.perform(get("/api/v1/users/versions/0")
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getAllUserVersions() throws Exception {
        mockMvc.perform(get("/api/v1/users/" + savedUser.getId() + "/versions")
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getAllUserVersionsPage() throws Exception{
        mockMvc.perform(get("/api/v1/users/" + savedUser.getId() + "/versions/0")
                    .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void setRoleSuccess() throws Exception{
        mockMvc.perform(put("/api/v1/admin/setRole/" + target.getId())
                    .with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("[\"AUTHOR\"]"))
                .andExpect(status().isOk());

        User updated = userRepository.findById(target.getId()).get();
        assertThat(updated.getRoles()).contains(ROLES.AUTHOR);
    }

    @Test
    public void setRoleWithoutAdmin() throws Exception{
        mockMvc.perform(put("/api/v1/admin/setRole/" + target.getId())
                        .with(user("admin").roles("READER"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("[\"AUTHOR\"]"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void setRoleAdminChangingOwnRole() throws Exception{
        mockMvc.perform(put("/api/v1/admin/setRole/" + savedUser.getId())
                    .with(user("admin").roles("ADMIN"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("[\"READER\"]"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteUserSuccess() throws Exception{
        mockMvc.perform(delete("/api/v1/admin/deleteUser/" + target.getId())
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk());

        assertThat(userRepository.findById(target.getId())).isEmpty();
    }

    @Test
    public void deleteUserWithoutAdmin() throws Exception{
        mockMvc.perform(delete("/api/v1/admin/deleteUser/" + target.getId())
                .with(user("user").roles("READER")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteUserAdminDeletingOwnAccount() throws Exception{
        mockMvc.perform(delete("/api/v1/admin/deleteUser/" + savedUser.getId())
                .with(user("admin").roles("ADMIN")))
                .andExpect(status().isBadRequest());
    }
}
