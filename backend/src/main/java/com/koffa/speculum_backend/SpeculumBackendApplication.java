package com.koffa.speculum_backend;

import com.koffa.speculum_backend.user.repositories.UserRepository;
import com.koffa.speculum_backend.user.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpeculumBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpeculumBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserRepository repository, PasswordEncoder encoder) {
		return args -> {
			if(repository.findByUsername("admin").isEmpty()) {
				User user = new User();
				user.setUsername("admin");
				user.setPassword(encoder.encode("password"));
				user.setRole("ADMIN");
				repository.save(user);
			}
			if(repository.findByUsername("user").isEmpty()) {
				User user = new User();
				user.setUsername("user");
				user.setPassword(encoder.encode("password"));
				user.setRole("USER");
				repository.save(user);
			}
		};
	}
}
