package com.nutriso.api.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nutriso.api.user.model.User;
import com.nutriso.api.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByEmailOrThrow(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }

}
