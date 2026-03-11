package com.sap.documentmgn;

import com.sap.documentmgn.entity.User;
import com.sap.documentmgn.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@SpringBootApplication
public class DocumentmgnApplication implements CommandLineRunner {
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

    public DocumentmgnApplication(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(DocumentmgnApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = new User();
		user.setUsername("gosho");
		user.setRole(Collections.singletonList("ADMIN"));
		user.setPassword(passwordEncoder.encode("1234"));
		userRepository.save(user);
	}
}
