package com.sap.documentmgn.controller;

import com.sap.documentmgn.dto.RegisterRequest;
import com.sap.documentmgn.dto.UserResponse;
import com.sap.documentmgn.service.UserService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;
    public AuthController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request){
        UserResponse response = userService.register(request);
        return ResponseEntity.ok(response);
    }
}
