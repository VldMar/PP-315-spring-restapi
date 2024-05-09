package org.mrchv.springrestapi.controller.rest;

import lombok.RequiredArgsConstructor;
import org.mrchv.springrestapi.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
@RequiredArgsConstructor
@RestController
public class RestRoleController {

    private final RoleService roleService;

    @GetMapping("/api/roles")
    public ResponseEntity<Set<String>> getAllRoles() {
        return ResponseEntity.ok(roleService.findAllRolesNames());
    }
}
