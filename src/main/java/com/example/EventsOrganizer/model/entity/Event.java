package com.example.EventsOrganizer.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private Date beginDate;
    private Date endDate;
    private String status;

    @ManyToMany()
    @JoinTable(name = "users_joined_the_events"
            , joinColumns = @JoinColumn(name = "joined_event_id")
            , inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> joinedUsers;

    @ManyToOne()
    @JoinColumn(name = "organizing_club_id")
    private Club organizingClub;

}
