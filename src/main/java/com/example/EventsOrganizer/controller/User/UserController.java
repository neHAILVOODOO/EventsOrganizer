package com.example.EventsOrganizer.controller.User;


import com.example.EventsOrganizer.model.dto.ClubDto;
import com.example.EventsOrganizer.model.dto.EventDto;
import com.example.EventsOrganizer.model.dto.UserDto;
import com.example.EventsOrganizer.service.ClubService;
import com.example.EventsOrganizer.service.EventService;
import com.example.EventsOrganizer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ClubService clubService;
    private final EventService eventService;


    @GetMapping()
    public List<UserDto> getUsers() {
       return userService.findAllUsers();
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable long userId) {
        return userService.findById(userId);
    }

    @GetMapping("/{userId}/joined-events")
    public List<EventDto> getEventsByUser(@PathVariable long userId) {
        return eventService.findAllByUser(userId);
    }

    @GetMapping("/{userId}/subscribed-clubs")
    public List<ClubDto> getClubsByUser(@PathVariable long   userId) {
        return clubService.findAllByUser(userId);
    }


}
