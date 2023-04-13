package com.challenge.socialNetwork.service;

import com.challenge.socialNetwork.data.dto.UserDto;
import com.challenge.socialNetwork.data.model.User;

import java.util.List;

public interface FriendsService {
    void newFriendRequest(Long id, UserDto friend);

    void acceptedFriendship(Long id, UserDto friend);

    void rejectedFriendship(Long id, UserDto friend);

    List<User> getFriends(Long id);
}
