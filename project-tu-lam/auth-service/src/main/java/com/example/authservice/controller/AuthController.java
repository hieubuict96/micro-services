package com.example.authservice.controller;

import com.example.authservice.dto.UserDTO;
import com.example.authservice.model.User;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.security.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
        UserDTO userDTO1 = new UserDTO(user);
        userDTO1.setToken(tokenProvider.generateTokenBySubject(userDTO1.getUsername()));
        return ResponseEntity.ok(userDTO1);
    }

    @PostMapping("/signin")
    public ResponseEntity<UserDTO> signin(@RequestBody UserDTO userDTO) throws Exception {
        Optional<User> user = userRepository.getUserByUsername(userDTO.getUsername());
        if (user.isPresent()) {
            UserDTO userDTO1 = new UserDTO(user.get());
            userDTO1.setToken(tokenProvider.generateTokenBySubject(user.get().getUsername()));
            return ResponseEntity.ok(userDTO1);
        } else {
            throw new Exception("server error");
        }
    }

    @PostMapping("/verify-token")
    public ResponseEntity<UserDTO> verifyToken(@RequestBody UserDTO userDTO) throws Exception {
        if (!tokenProvider.validateToken(userDTO.getToken())) {
            throw new Exception("invalid token");
        }

        String username = tokenProvider.getSubjectByToken(userDTO.getToken());
        Optional<User> user = userRepository.getUserByUsername(username);

        if (user.isPresent()) {
            UserDTO userDTO1 = new UserDTO(user.get());
            userDTO1.setToken(userDTO.getToken());
            return ResponseEntity.ok(userDTO1);
        } else {
            throw new Exception("server error");
        }
    }
}
