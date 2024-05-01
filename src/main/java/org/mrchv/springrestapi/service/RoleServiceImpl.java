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
    public Set<String> findAllRoles() {
        return roleRepo.findAll()
                .stream()
                .map(Role::toString)
                .collect(Collectors.toSet());
    }
}
