package org.mrchv.springrestapi.controller.rest;

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

    private final UserMapper userMapper;

    @GetMapping("principal")
    public ResponseEntity<UserDto> getPrincipal(@AuthenticationPrincipal UserDetails principal) {
        return ResponseEntity.ok(userMapper.mapToUserDto((User) principal));
    }
}
