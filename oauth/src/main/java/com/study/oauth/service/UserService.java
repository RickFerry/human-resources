package com.study.oauth.service;

import com.study.oauth.feign.UserFeignClient;
import com.study.oauth.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserFeignClient userFeignClient;

    public User findByEmail(String email) {
        ResponseEntity<User> response = userFeignClient.findByEmail(email);
        return Optional.ofNullable(response.getBody())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        ResponseEntity<User> response = userFeignClient.findByEmail(userName);
        return Optional.ofNullable(response.getBody())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
