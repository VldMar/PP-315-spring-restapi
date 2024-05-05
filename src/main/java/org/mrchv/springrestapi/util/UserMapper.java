package org.mrchv.springrestapi.util;

import org.mrchv.springrestapi.dto.UserDto;
import org.mrchv.springrestapi.model.Role;
import org.mrchv.springrestapi.model.User;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toSet;

@Component
public final class UserMapper {

    public UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getAge(),
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(Role::toString)
                        .collect(toSet())
        );
    }

    public User mapToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.id())
                .name(userDto.firstName())
                .lastName(userDto.lastName())
                .age(userDto.age())
                .email(userDto.email())
                .password(userDto.password())
                .roles(userDto.roles().stream()
                        .map(roleName -> new Role(roleName))
                        .collect(toSet()))
                .build();
    }
}
