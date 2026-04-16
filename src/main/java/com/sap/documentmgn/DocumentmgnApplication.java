package com.sap.documentmgn;

import com.sap.documentmgn.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DocumentmgnApplication {
    public static void main(String[] args) {
        SpringApplication.run(DocumentmgnApplication.class, args);
    }
}