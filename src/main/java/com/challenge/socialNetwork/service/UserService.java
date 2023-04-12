package com.challenge.socialNetwork.service;

import com.challenge.socialNetwork.data.dto.UserDto;
import com.challenge.socialNetwork.data.model.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
