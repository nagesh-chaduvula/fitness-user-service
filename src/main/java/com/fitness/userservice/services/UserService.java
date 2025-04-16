package com.fitness.userservice.services;

import com.fitness.userservice.dto.UserRegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.entities.UserEntity;

public interface UserService {
    UserResponse registerUser(UserRegisterRequest userRegisterRequest);

    UserResponse getUserProfile(String userId);

    Boolean existByUserId(String userId);
}
