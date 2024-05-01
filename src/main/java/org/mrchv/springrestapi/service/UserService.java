package org.mrchv.springrestapi.service;

import org.mrchv.springrestapi.dto.UserDto;

import java.util.List;

public interface UserService {
     List<UserDto> findAllUsers();
     UserDto findUserById(Long id);
     UserDto findUserByUsername(String username);
     void updateUser(UserDto user);
     void deleteUserById(Long id);

     UserDto createUser(UserDto userDto);
}
