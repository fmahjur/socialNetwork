package com.challenge.socialNetwork.service.Impl;

import com.challenge.socialNetwork.data.dto.UserDto;
import com.challenge.socialNetwork.data.mapper.UserMapper;
import com.challenge.socialNetwork.data.model.Role;
import com.challenge.socialNetwork.data.model.User;
import com.challenge.socialNetwork.data.repository.UserRepository;
import com.challenge.socialNetwork.exception.NotFoundException;
import com.challenge.socialNetwork.service.RoleService;
import com.challenge.socialNetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setFirstname(userDto.getFirstName());
        user.setLastname(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleService.findByRoleName("Role_USER");
        if (role == null) {
            role = roleService.saveRole("Role_USER");
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("user not found!"));
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> UserMapper.INSTANCE.modelToUserDto(user))
                .collect(Collectors.toList());
    }


}
