package com.learn.security;

import java.util.HashSet;

import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.learn.security.entity.AppUser;
import com.learn.security.entity.Role;
import com.learn.security.repository.RoleRepository;
import com.learn.security.repository.UserRepository;

@SpringBootApplication
public class AuthSpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthSpringSecurityApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder) {
		return args -> {
			if (roleRepository.findByAuthority("ADMIN").isPresent())
				return;
				
			Role adminRole = roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("USER"));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);
			userRepository.save(new AppUser(1, "admin", passwordEncoder.encode("admin"), roles));
		};
	}

}
