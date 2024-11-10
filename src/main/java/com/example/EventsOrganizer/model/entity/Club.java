package com.example.EventsOrganizer.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clubs")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private String thematics;


    @ManyToMany()
    @JoinTable(name = "users_subscribed_to_clubs"
            , joinColumns = @JoinColumn(name = "subscribed_club_id")
            , inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonBackReference("subscribedClubsReference")
    private List<User> users;

    @OneToOne
    @JoinColumn(name = "owner_id")
    @JsonBackReference("ownClubReference")
    private User owner;

    @OneToMany(mappedBy = "organizingClub",cascade = CascadeType.REMOVE)
    @JsonBackReference("eventsReference")
    private List<Event> events;


}
