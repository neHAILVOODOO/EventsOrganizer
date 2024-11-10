package com.example.EventsOrganizer.model.dto;

import com.example.EventsOrganizer.model.entity.Club;
import com.example.EventsOrganizer.model.entity.Event;
import com.example.EventsOrganizer.model.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {

    private long id;
    private String login;
    private String password;
    private String name;
    private String surname;
    private String phoneNumber;
    private int age;
    private List<Role> roles;

    private List<Club> subscribedClubs;
    private Club ownClub;
    private List<Event> joinedEvents;


}
