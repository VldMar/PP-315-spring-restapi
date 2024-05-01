package org.mrchv.springrestapi.service;

import lombok.RequiredArgsConstructor;
import org.mrchv.springrestapi.dto.UserDto;
import org.mrchv.springrestapi.mapper.UserMapper;
import org.mrchv.springrestapi.model.User;
import org.mrchv.springrestapi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final RoleService roleService;
    private final PasswordEncoder encoder;

    @Override
    public List<UserDto> findAllUsers() {
        return userRepo.findAll()
                .stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findUserById(Long id) {
        return UserMapper.mapToUserDto(userRepo.getById(id));
    }

    @Override
    public UserDto findUserByUsername(String username) {
        return UserMapper.mapToUserDto(userRepo.findByEmail(username));
    }


    @Override
    public void updateUser(UserDto user) {
        UserDto userFromDB = findUserById(user.getId());

    }

    @Override
    public void deleteUserById(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        UserDto userFromDB = this.findUserByUsername(userDto.getEmail());
        if (userFromDB == null) {
            throw new RuntimeException("Пользователь с username=%s уже существует!".formatted(userDto.getEmail()));
        }
        if (userDto.getRoles().contains("ADMIN")) {
            userDto.setRoles(roleService.findAllRoles());
        }
        userDto.setPassword(encoder.encode(userDto.getPassword()));

        User savedUser = userRepo.save(UserMapper.mapToUser(userDto));

        return UserMapper.mapToUserDto(savedUser);
    }
}
