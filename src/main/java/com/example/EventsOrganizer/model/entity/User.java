package com.example.EventsOrganizer.model.entity;




import lombok.*;

import javax.persistence.*;
import java.util.List;


@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;
    private String name;
    private String surname;
    private String phoneNumber;
    private int age;

    @ManyToMany()
    @JoinTable(name = "users_subscribed_to_clubs"
            , joinColumns = @JoinColumn(name = "user_id")
            , inverseJoinColumns = @JoinColumn(name = "subscribed_club_id")
    )
    private List<Club> subscribedClubs;

    @OneToOne(mappedBy = "owner")
    private Club ownClub;

    @ManyToMany()
    @JoinTable(name = "users_joined_the_events"
            , joinColumns = @JoinColumn(name = "user_id")
            , inverseJoinColumns = @JoinColumn(name = "joined_event_id")
    )
    private List<Event> joinedEvents;

}
