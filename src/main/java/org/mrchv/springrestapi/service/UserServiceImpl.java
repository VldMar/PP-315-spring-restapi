package org.mrchv.springrestapi.service;

import lombok.RequiredArgsConstructor;
import org.mrchv.springrestapi.model.Role;
import org.mrchv.springrestapi.model.User;
import org.mrchv.springrestapi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final RoleService roleService;
    private final PasswordEncoder encoder;

    @Override
    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public User createUser(User user) {
        this.findUserByEmail(user.getEmail())
                .ifPresent(userDb ->
                        new RuntimeException("Пользователь с username=%s уже существует!".formatted(userDb.getEmail()))
                );


        if (isUserAdmin(user)) {
            user.setRoles(roleService.findAllRoles());
        } else {
            user.setRoles(
                    user.getRoles()
                            .stream()
                            .map(role ->
                                    roleService.findRoleByName(role.getName()))
                            .collect(Collectors.toSet())
            );
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public User updateUser(Long userId, User user) {
        User userFromDB = userRepo.findById(userId)
                .orElseThrow(NoSuchElementException::new);

        if (isUserAdmin(user)) {
            user.setRoles(roleService.findAllRoles());
        } else {
            user.setRoles(
                    user.getRoles()
                            .stream()
                            .map(role ->
                                    roleService.findRoleByName(role.getName()))
                            .collect(Collectors.toSet())
            );
        }

        String password = user.getPassword().equals("")
                ?   userFromDB.getPassword()
                :   encoder.encode(user.getPassword());

        user.setPassword(password);
        return userRepo.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepo.deleteById(id);
    }

    private boolean isUserAdmin(User user) {
        return user.getRoles()
                .stream()
                .map(Role::getName)
                .filter(roleName -> roleName.equals("ADMIN"))
                .count() > 0;
    }
}
