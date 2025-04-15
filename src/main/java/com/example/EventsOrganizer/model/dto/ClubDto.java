package com.example.EventsOrganizer.model.dto;

import com.example.EventsOrganizer.model.entity.Event;
import com.example.EventsOrganizer.model.entity.User;
import lombok.*;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClubDto {

    private long id;
    private String name;
    private String description;
    private String thematics;

    private List<User> users;
    private User owner;
    private List<Event> events;


}
