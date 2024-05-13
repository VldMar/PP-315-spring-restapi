package org.mrchv.springrestapi.service;

import org.mrchv.springrestapi.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
     List<User> findAllUsers();
     Optional<User> findUserById(Long id);
     Optional<User> findUserByEmail(String email);
     User createUser(User user);
     User updateUser(Long id, User user);
     void deleteUserById(Long id);
}
