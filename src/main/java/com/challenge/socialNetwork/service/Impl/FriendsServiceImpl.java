package com.challenge.socialNetwork.service.Impl;

import com.challenge.socialNetwork.data.dto.UserDto;
import com.challenge.socialNetwork.data.enaums.FriendshipStatus;
import com.challenge.socialNetwork.data.model.Friends;
import com.challenge.socialNetwork.data.model.User;
import com.challenge.socialNetwork.data.repository.FriendsRepository;
import com.challenge.socialNetwork.exception.NotFoundException;
import com.challenge.socialNetwork.service.FriendsService;
import com.challenge.socialNetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendsServiceImpl implements FriendsService {
    FriendsRepository friendsRepository;

    UserService userService;

    @Override
    public void newFriendRequest(Long id, UserDto friend) {
        User firstUser = userService.findUserById(id);
        User secondUser = userService.findUserByEmail(friend.getEmail());

        Friends friends = friendsRepository.checkingFriendShip(firstUser, secondUser).orElse(null);

        if (friends == null) {
            friends = new Friends();
            friends.setFirstUser(firstUser);
            friends.setSecondUser(secondUser);
            friends.setFriendshipStatus(FriendshipStatus.REQUESTED);
            friendsRepository.save(friends);
        } else if (friends.getFirstUser().equals(firstUser) & friends.getFriendshipStatus().equals(FriendshipStatus.ACCEPTED)) {
            friendsRepository.updateFriendShipStatus(firstUser, secondUser, FriendshipStatus.MUTUAL);
        }
    }

    @Override
    public void acceptedFriendship(Long id, UserDto friend) {
        User firstUser = userService.findUserById(id);
        User secondUser = userService.findUserByEmail(friend.getEmail());
        friendsRepository.updateFriendShipStatus(firstUser, secondUser, FriendshipStatus.ACCEPTED);
    }

    @Override
    public void rejectedFriendship(Long id, UserDto friend) {
        User firstUser = userService.findUserById(id);
        User secondUser = userService.findUserByEmail(friend.getEmail());
        Friends friends = friendsRepository.checkingFriendShip(firstUser, secondUser).orElseThrow(() -> new NotFoundException("You are not a friend!"));
        friendsRepository.delete(friends);
    }

    @Override
    public List<User> getFriends(Long id) {
        User currentUser = userService.findUserById(id);
        List<Friends> friendsByFirstUser = friendsRepository.findByFirstUser(currentUser);
        List<Friends> friendsBySecondUser = friendsRepository.findBySecondUser(currentUser);
        List<User> friendUsers = new ArrayList<>();

        for (Friends friend : friendsByFirstUser) {
            friendUsers.add(userService.findUserById(friend.getSecondUser().getId()));
        }
        for (Friends friend : friendsBySecondUser) {
            friendUsers.add(userService.findUserById(friend.getFirstUser().getId()));
        }
        return friendUsers;
    }
}
