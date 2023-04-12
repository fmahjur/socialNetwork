package com.challenge.socialNetwork.data.repository;

import com.challenge.socialNetwork.data.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
