package org.mrchv.springrestapi.controller.rest;

import lombok.RequiredArgsConstructor;
import org.mrchv.springrestapi.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RestUserController {

    @GetMapping("/principal")
    public ResponseEntity<User> getPrincipal(@AuthenticationPrincipal UserDetails principal) {
        return ResponseEntity.ok((User) principal);
    }
}
