package org.mrchv.springrestapi.service;

import org.mrchv.springrestapi.model.Role;

import java.util.Set;

public interface RoleService {
    Set<Role> findAllRoles();
    Set<String> findAllRolesNames();
    Role findRoleByName(String name);
    Role saveRole(String roleName);
}
