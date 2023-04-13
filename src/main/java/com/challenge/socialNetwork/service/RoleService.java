package com.challenge.socialNetwork.service;

import com.challenge.socialNetwork.data.model.Role;

public interface RoleService {

    Role newRole(String roleName);
    Role findByRoleName(String roleName);

}
