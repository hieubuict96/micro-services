package com.javatechie.dto;

import java.util.HashSet;
import java.util.Set;

public class UserDTO {

    private Long id;
    private String username;
    private String password;

    private String token;

    private Set<Authority> authorities = new HashSet<>();

    public UserDTO() {
    }

    public UserDTO(String token) {
        this.token = token;
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

    public static class Authority {
        private String authority;

        public Authority() {
        }

        public Authority(String authority) {
            this.authority = authority;
        }

        public String getAuthority() {
            return authority;
        }

        public void setAuthority(String authority) {
            this.authority = authority;
        }
    }
}
