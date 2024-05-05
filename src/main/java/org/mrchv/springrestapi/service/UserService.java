package org.mrchv.springrestapi.service;

import org.mrchv.springrestapi.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
     List<UserDto> findAllUsers();
     Optional<UserDto> findUserById(Long id);
     Optional<UserDto> findUserByEmail(String email);
     UserDto createUser(UserDto userDto);
     UserDto updateUser(UserDto user);
     void deleteUserById(Long id);
}
