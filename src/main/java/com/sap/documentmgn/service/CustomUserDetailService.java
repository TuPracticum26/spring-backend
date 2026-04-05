package com.sap.documentmgn.service;

import com.sap.documentmgn.entity.ROLES;
import com.sap.documentmgn.entity.User;
import com.sap.documentmgn.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true) //<-- Оправи автентикацията
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // findByUsername вече връща Optional<User>
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        String[] rolesArray = user.getRoles()
                .stream()
                .map(ROLES::name)
                .toArray(String[]::new);

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(rolesArray)
                .build();
    }
}
