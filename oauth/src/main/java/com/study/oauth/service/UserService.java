package com.study.oauth.service;

import com.study.oauth.feign.UserFeignClient;
import com.study.oauth.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserFeignClient userFeignClient;

    public User findByEmail(String email) {
        ResponseEntity<User> response = userFeignClient.findByEmail(email);
        return Optional.ofNullable(response.getBody())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
