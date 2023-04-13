package com.challenge.socialNetwork.controller;

import com.challenge.socialNetwork.data.dto.UserDto;
import com.challenge.socialNetwork.data.model.User;
import com.challenge.socialNetwork.service.FriendsService;
import com.challenge.socialNetwork.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final FriendsService friendsService;

    @GetMapping("index")
    public String home(){
        return "index";
    }

    @GetMapping("register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model) {
        User existing = userService.findUserByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.registerUser(user);
        return "redirect:/register?success";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/users")
    public String listRegisteredUsers(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/friend-request")
    public ResponseEntity<?> requestForStartedFriendship(@RequestBody UserDto userDto, Authentication authentication){
        User authenticatedUser = (User) authentication.getPrincipal();
        friendsService.newFriendRequest(authenticatedUser.getId(), userDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/accept-friend-request")
    public ResponseEntity<?> acceptFriendRequest(@RequestBody UserDto userDto, Authentication authentication) {
        User authenticatedUser = (User) authentication.getPrincipal();
        friendsService.acceptedFriendship(authenticatedUser.getId(), userDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/reject-friend-request")
    public ResponseEntity<?> rejectFriendRequest(@RequestBody UserDto userDto, Authentication authentication) {
        User authenticatedUser = (User) authentication.getPrincipal();
        friendsService.rejectedFriendship(authenticatedUser.getId(), userDto);
        return ResponseEntity.ok().build();
    }

}
