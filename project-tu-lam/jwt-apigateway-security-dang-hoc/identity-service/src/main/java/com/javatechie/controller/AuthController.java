package com.javatechie.controller;

import com.javatechie.config.UserDetailsImpl;
import com.javatechie.dto.UserDTO;
import com.javatechie.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody UserDTO user) {
        return service.saveUser(user);
    }

    @PostMapping("/signin")
    public ResponseEntity<Object> signin(@RequestBody UserDTO userDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
            if (authentication.isAuthenticated()) {
                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                UserDTO userResponse = new UserDTO(userDetails);
                userResponse.setToken(service.generateToken(userDetails.getUsername()));
                return ResponseEntity.ok(userResponse);
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<Object> validateToken(@RequestBody UserDTO userDTO) {
        return service.validateToken(userDTO.getToken());
    }
}
