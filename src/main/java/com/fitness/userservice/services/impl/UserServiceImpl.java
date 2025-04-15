package com.fitness.userservice.services.impl;

import com.fitness.userservice.dto.UserRegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.entities.UserEntity;
import com.fitness.userservice.repositories.UserRepository;
import com.fitness.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse registerUser(UserRegisterRequest userRegisterRequest) {
        if (userRepository.existsByEmail(userRegisterRequest.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userRegisterRequest.getEmail());
        userEntity.setPassword(userRegisterRequest.getPassword());
        userEntity.setFirstName(userRegisterRequest.getFirstName());
        userEntity.setLastName(userRegisterRequest.getLastName());
        UserEntity savedUserEntity = userRepository.save(userEntity);

        UserResponse userResponse = mapToUserResponse(savedUserEntity);
        return userResponse;
    }

    @Override
    public UserResponse getUserProfile(String userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return mapToUserResponse(userEntity);
    }

    private UserResponse mapToUserResponse(UserEntity savedUserEntity) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(savedUserEntity.getId());
        userResponse.setEmail(savedUserEntity.getEmail());
        userResponse.setFirstName(savedUserEntity.getFirstName());
        userResponse.setLastName(savedUserEntity.getLastName());
        userResponse.setUserRole(savedUserEntity.getUserRole().toString());
        userResponse.setCreatedAt(savedUserEntity.getCreatedAt().toString());
        userResponse.setUpdatedAt(savedUserEntity.getUpdatedAt().toString());
        return userResponse;
    }
}
