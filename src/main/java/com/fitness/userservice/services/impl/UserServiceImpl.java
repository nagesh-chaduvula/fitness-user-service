package com.fitness.userservice.services.impl;

import com.fitness.userservice.dto.UserRegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.entities.UserEntity;
import com.fitness.userservice.enums.UserRoleEnum;
import com.fitness.userservice.repositories.UserRepository;
import com.fitness.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse registerUser(UserRegisterRequest userRegisterRequest) {
        log.info("***** Registering user with email: {}", userRegisterRequest.getEmail());

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
        log.info("***** Fetching user profile for user ID: {}", userId);
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return mapToUserResponse(userEntity);
    }

    @Override
    public Boolean existByUserId(String userId) {
        log.info("***** Checking existence of user ID: {}", userId);
        return userRepository.existsById(userId);
    }

    private UserResponse mapToUserResponse(UserEntity savedUserEntity) {
        log.info("***** Mapping UserEntity to UserResponse for user ID: {}", savedUserEntity.getId());
        UserResponse userResponse = new UserResponse();
        userResponse.setId(savedUserEntity.getId());
        userResponse.setEmail(savedUserEntity.getEmail());
        userResponse.setFirstName(savedUserEntity.getFirstName());
        userResponse.setLastName(savedUserEntity.getLastName());
        userResponse.setUserRole(UserRoleEnum.decode(savedUserEntity.getUserRole().getRole()).getRole());
        userResponse.setCreatedAt(savedUserEntity.getCreatedAt().toString());
        userResponse.setUpdatedAt(savedUserEntity.getUpdatedAt().toString());
        return userResponse;
    }
}
