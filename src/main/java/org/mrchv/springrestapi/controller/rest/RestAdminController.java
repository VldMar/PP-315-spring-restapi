package org.mrchv.springrestapi.controller.rest;

import lombok.RequiredArgsConstructor;
import org.mrchv.springrestapi.model.User;
import org.mrchv.springrestapi.service.RoleService;
import org.mrchv.springrestapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class RestAdminController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(userService.findUserById(userId).get());
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long userId, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(userId, user));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok("User id=%s deleted successfully".formatted(userId));
    }

    @GetMapping("/roles")
    public ResponseEntity<Set<String>> getAllRoles() {
        return ResponseEntity.ok(roleService.findAllRolesNames());
    }
}
