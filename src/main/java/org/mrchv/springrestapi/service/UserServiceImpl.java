package org.mrchv.springrestapi.service;

import lombok.RequiredArgsConstructor;
import org.mrchv.springrestapi.dto.UserDto;
import org.mrchv.springrestapi.model.User;
import org.mrchv.springrestapi.repository.RoleRepository;
import org.mrchv.springrestapi.repository.UserRepository;
import org.mrchv.springrestapi.util.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final RoleService roleService;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> findAllUsers() {
        return userRepo.findAll()
                .stream()
                .map(userMapper::mapToUserDto)
                .collect(toList());
    }

    @Override
    public Optional<UserDto> findUserById(Long id) {
        return userRepo.findById(id)
                .map(userMapper::mapToUserDto);
    }

    @Override
    public Optional<UserDto> findUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .map(userMapper::mapToUserDto);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        this.findUserByEmail(userDto.email())
                .orElseThrow(() ->
                        new RuntimeException("Пользователь с username=%s уже существует!".formatted(userDto.email()))
                );

        User user = userMapper.mapToUser(userDto);

        if (userDto.roles().contains("ADMIN")) {
            user.setRoles(roleService.findAllRoles());
        }

        user.setPassword(encoder.encode(userDto.password()));

        User savedUser = userRepo.save(userMapper.mapToUser(userDto));
        return userMapper.mapToUserDto(savedUser);
    }

    @Override
    public void updateUser(UserDto userDto) {
        UserDto userFromDB = findUserById(userDto.id()).orElseThrow(NoSuchElementException::new);
        User user = userMapper.mapToUser(userDto);
        if (userFromDB.roles().contains("ADMIN")) {
            user.setRoles(roleService.findAllRoles());
        }

        String password = userDto.password() == ""
                ?   userFromDB.password()
                :   encoder.encode(userDto.password());

        user.setPassword(password);
        userRepo.save(userMapper.mapToUser(userDto));
    }

    @Override
    public void deleteUserById(Long id) {
        userRepo.deleteById(id);
    }
}
