package com.challenge.socialNetwork.data.repository;

import com.challenge.socialNetwork.data.enaums.FriendshipStatus;
import com.challenge.socialNetwork.data.model.Friends;
import com.challenge.socialNetwork.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendsRepository extends JpaRepository<Friends, Long> {
    List<Friends> findByFirstUser(User user);

    List<Friends> findBySecondUser(User user);

    @Query("select f from Friends f " +
            "where (f.firstUser= :first or f.secondUser= :first) and (f.firstUser= :second or f.secondUser=:second) ")
    Optional<Friends> checkingFriendShip(User first, User second);

    @Modifying
    @Query("update Friends f set f.friendshipStatus = :friendshipStatus " +
            "where (f.firstUser= :first or f.secondUser= :first) and (f.firstUser= :second or f.secondUser=:second)")
    void updateFriendShipStatus(User first, User second, FriendshipStatus friendshipStatus);

}
