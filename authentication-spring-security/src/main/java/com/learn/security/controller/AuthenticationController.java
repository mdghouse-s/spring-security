package com.learn.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.security.dto.LoginResponseDto;
import com.learn.security.dto.RegistrationDto;
import com.learn.security.entity.AppUser;
import com.learn.security.service.AuthenticationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@Slf4j
public class AuthenticationController {
    
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public AppUser register(@RequestBody RegistrationDto user ) {
        log.info("Registration request received for user: {}", user.getUsername());
        return authenticationService.register(user.getUsername(), user.getPassword());
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody RegistrationDto user) {
        log.info("Login request received for user: {}", user.getUsername());
        return authenticationService.loginUser(user.getUsername(), user.getPassword());
    }    

}
