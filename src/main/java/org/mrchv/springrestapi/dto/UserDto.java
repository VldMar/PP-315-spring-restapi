package org.mrchv.springrestapi.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Set;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UserDto(
    Long id,
    String firstName,
    String lastName,
    int age,
    String email,
    String newPassword,
    Set<String> roles
) {}
