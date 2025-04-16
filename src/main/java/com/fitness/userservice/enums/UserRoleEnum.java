package com.fitness.userservice.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum UserRoleEnum {
    USER("U"),
    ADMIN("A");

    @JsonValue
    private final String role;

    @JsonCreator
    public static UserRoleEnum decode(String userRole) {
        return Stream.of(UserRoleEnum.values())
                .filter(targetEnum -> Objects.equals(targetEnum.role, userRole))
                .findFirst()
                .orElse(UserRoleEnum.USER);


    }

}
