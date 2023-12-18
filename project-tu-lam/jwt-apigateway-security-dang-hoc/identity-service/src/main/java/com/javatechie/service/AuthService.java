package com.javatechie.service;

import com.javatechie.config.UserDetailsImpl;
import com.javatechie.config.UserDetailsServiceImpl;
import com.javatechie.dto.ExceptionDTO;
import com.javatechie.dto.UserDTO;
import com.javatechie.entity.User;
import com.javatechie.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public ResponseEntity<Object> saveUser(UserDTO credential) {
        try {
            User user = credential.toEntity();
            user.setPassword(passwordEncoder.encode(credential.getPassword()));
            user = userRepository.save(user);
            return ResponseEntity.ok(new UserDTO(user));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(new ExceptionDTO(null, "server error"));
        }
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public ResponseEntity<Object> validateToken(String token) {
        try {
            String username = jwtService.validateToken(token);
            UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);
            return new ResponseEntity<>(userDetails, HttpStatus.OK);
        } catch (UsernameNotFoundException | IllegalArgumentException | SignatureException | MalformedJwtException |
                 UnsupportedJwtException | ExpiredJwtException ex) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
