package com.challenge.socialNetwork.service;

import com.challenge.socialNetwork.data.model.Role;

public interface RoleService {

    Role saveRole(String roleName);
    Role findByRoleName(String roleName);

}
