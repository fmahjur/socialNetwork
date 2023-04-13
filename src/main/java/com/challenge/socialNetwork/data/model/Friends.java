package com.challenge.socialNetwork.data.model;

import com.challenge.socialNetwork.data.enaums.FriendshipStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name="friends")
public class Friends {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne(cascade = CascadeType.ALL)
    User firstUser;

    @OneToOne(cascade = CascadeType.ALL)
    User secondUser;

    @CreationTimestamp
    LocalDateTime friendshipDate;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    FriendshipStatus friendshipStatus;

    public Friends(User firstUser, User secondUser) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.friendshipStatus = FriendshipStatus.REQUESTED;
    }
}
