package com.citamedica.salud.citamedica.controllers;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.citamedica.salud.citamedica.dto.AuthCreateUserRequest;
import com.citamedica.salud.citamedica.dto.AuthCreateUserResponse;
import com.citamedica.salud.citamedica.dto.AuthLoginRequest;
import com.citamedica.salud.citamedica.dto.AuthResponse;
import com.citamedica.salud.citamedica.service.UserDetailsServiceImpl;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Go To Auth Server
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest entity) {
        return new ResponseEntity<>(this.userDetailsService.loginUser(entity), HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthCreateUserResponse> register(@RequestBody @Valid AuthCreateUserRequest entity) throws BadRequestException{

        return new ResponseEntity<>(this.userDetailsService.createUser(entity), HttpStatus.CREATED);
    }

}
