package org.mrchv.springrestapi.mapper;

import org.mrchv.springrestapi.dto.UserDto;
import org.mrchv.springrestapi.model.Role;
import org.mrchv.springrestapi.model.User;

import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;


public final class UserMapper {

    public static UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getName())
                .lastName(user.getLastName())
                .age(user.getAge())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles()
                        .stream()
                        .map(Role::toString)
                        .collect(toSet())
                )
                .build();
    }

    public static User mapToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .age(userDto.getAge())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .roles(userDto.getRoles()
                        .stream()
                        .map(Role::new)
                        .collect(toSet()))
                .build();
    }
}
