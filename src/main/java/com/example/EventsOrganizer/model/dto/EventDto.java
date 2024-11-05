package com.example.EventsOrganizer.model.dto;

import com.example.EventsOrganizer.model.entity.Club;
import com.example.EventsOrganizer.model.entity.User;
import lombok.*;
import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EventDto {

    private long id;
    private String name;
    private String description;
    private Date beginDate;
    private Date endDate;
    private String status;

    private List<User> joinedUsers;
    private Club organizingClub;

}
