package org.mrchv.springrestapi.security;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.mrchv.springrestapi.model.Role;
import org.mrchv.springrestapi.model.User;
import org.mrchv.springrestapi.service.RoleService;
import org.mrchv.springrestapi.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final RoleService roleService;

    @PostConstruct
    public void init() {
        Role adminRole = roleService.saveRole("ADMIN");
        Role userRole = roleService.saveRole("USER");

        User admin = new User(
                1L,
                "ivan",
                "ivanov",
                5,
                "admin@mail.ru",
                "admin",
                Set.of(adminRole, userRole)
        );
        userService.createUser(admin);

        User user = new User(
                2L,
                "vladimir",
                "marychev",
                6,
                "user@mail.ru",
                "user",
                Set.of(userRole)
        );
        userService.createUser(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService
                .findUserByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Пользователь %s не найден".formatted(username))
                );
    }
}
