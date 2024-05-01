package org.mrchv.springrestapi.repository;

import org.mrchv.springrestapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Role findRoleByName(String name);
}
