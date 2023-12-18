package com.javatechie.dto;

import com.javatechie.config.UserDetailsImpl;
import com.javatechie.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDTO {

    private Long id;
    private String username;
    private String password;

    private String token;

    private Set<Authority> authorities = new HashSet<>();

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        for (String role : user.getRoles().split(",")) {
            authorities.add(new Authority(role));
        }
    }

    public UserDTO(UserDetailsImpl userDetails) {
        this.id = userDetails.getId();
        this.username = userDetails.getUsername();
        this.authorities = userDetails.getAuthorities().stream().map(Authority::new).collect(Collectors.toSet());
    }

    public User toEntity() {
        User user = new User();
        user.setId(this.id);
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setRoles(authorities.stream().map(Authority::getAuthority).collect(Collectors.joining(",")));
        return user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public static class Authority implements GrantedAuthority {
        private String authority;

        public Authority() {
        }

        public Authority(String authority) {
            this.authority = authority;
        }

        public Authority(GrantedAuthority grantedAuthority) {
            this.authority = grantedAuthority.getAuthority();
        }

        @Override
        public String getAuthority() {
            return authority;
        }

        public void setAuthority(String authority) {
            this.authority = authority;
        }
    }
}
