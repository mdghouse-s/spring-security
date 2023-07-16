package com.learn.security.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learn.security.dto.LoginResponseDto;
import com.learn.security.entity.AppUser;
import com.learn.security.entity.Role;
import com.learn.security.repository.RoleRepository;
import com.learn.security.repository.UserRepository;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtTokenService jwtTokenService;

    public AppUser register(String username, String password) {
        Role role = roleRepository.findByAuthority("USER").orElseThrow(() -> new RuntimeException("Role not found"));

        Set<Role> roles = new HashSet<>(Set.of(role));
        roles.add(role);

        return userRepository.save(new AppUser(0,username, passwordEncoder.encode(password), roles));
    }

    public LoginResponseDto loginUser(String username, String password) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        AppUser user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"))    ;
        String token = jwtTokenService.createToken(authentication);

        return new LoginResponseDto(user.getUsername(), token);
    }
}
