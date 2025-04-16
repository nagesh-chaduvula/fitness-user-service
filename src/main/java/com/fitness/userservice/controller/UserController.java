package com.fitness.userservice.controller;

import com.fitness.userservice.dto.UserRegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable String userId) {
        log.info("***** Received request to get user profile for user ID: {}", userId);
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        log.info("***** Received user registration request: {}", userRegisterRequest);
        return ResponseEntity.ok(userService.registerUser(userRegisterRequest));
    }

    @GetMapping("/{userId}/validate")
    public ResponseEntity<Boolean> validateUserId(@PathVariable String userId) {
        log.info("***** Received request to validate user ID: {}", userId);
        return ResponseEntity.ok(userService.existByUserId(userId));
    }
}
