package com.divya.userService.UserService.repository;

import com.divya.userService.UserService.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {

    public Role findRoleByName(String value);

    @Override
    <S extends Role> S save(S entity);
}
