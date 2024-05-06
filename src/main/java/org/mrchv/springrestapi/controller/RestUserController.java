package org.mrchv.springrestapi.controller;

import lombok.RequiredArgsConstructor;
import org.mrchv.springrestapi.dto.UserDto;
import org.mrchv.springrestapi.model.User;
import org.mrchv.springrestapi.service.UserService;
import org.mrchv.springrestapi.util.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class RestUserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("principal")
    public ResponseEntity<UserDto> getPrincipal(@AuthenticationPrincipal UserDetails principal) {
        UserDto principalDto = userService.findUserById(1L).get();//userMapper.mapToUserDto((User) principal);
        return ResponseEntity.ok(principalDto);
    }
}
