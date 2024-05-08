package org.mrchv.springrestapi.util;

import lombok.RequiredArgsConstructor;
import org.mrchv.springrestapi.dto.UserDto;
import org.mrchv.springrestapi.model.Role;
import org.mrchv.springrestapi.model.User;
import org.mrchv.springrestapi.service.RoleService;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toSet;

@Component
@RequiredArgsConstructor
public final class UserMapper {

    private final RoleService roleService;

    public UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getAge(),
                user.getEmail(),
                "",
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
                .password(userDto.newPassword())
                .roles(userDto.roles().stream()
                        .map(roleName -> roleService.findRoleByName(roleName))
                        .collect(toSet()))
                .build();
    }
}
