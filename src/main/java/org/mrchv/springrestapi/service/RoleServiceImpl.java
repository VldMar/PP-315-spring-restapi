package org.mrchv.springrestapi.service;

import lombok.RequiredArgsConstructor;
import org.mrchv.springrestapi.model.Role;
import org.mrchv.springrestapi.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepo;

    @Override
    public Set<Role> findAllRoles() {
        return Set.copyOf(roleRepo.findAll());
    }

    @Override
    public Set<String> findAllRolesNames() {
        return roleRepo.findAll()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

    @Override
    public Role findRoleByName(String name) {
        return roleRepo.findRoleByName(name).get();
    }

    @Override
    public Role saveRole(String roleName) {
        Role savedRole = roleRepo.saveAndFlush(new Role(roleName));
        return savedRole;
    }
}
