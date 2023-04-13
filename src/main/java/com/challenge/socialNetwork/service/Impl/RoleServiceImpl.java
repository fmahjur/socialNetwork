package com.challenge.socialNetwork.service.Impl;

import com.challenge.socialNetwork.data.model.Role;
import com.challenge.socialNetwork.data.repository.RoleRepository;
import com.challenge.socialNetwork.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role saveRole(String roleName) {
        Role role = new Role();
        role.setName(roleName);
        return roleRepository.save(role);
    }

    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findByName(roleName);
    }
}
