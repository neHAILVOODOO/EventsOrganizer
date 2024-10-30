package com.example.EventsOrganizer.controller;


import com.example.EventsOrganizer.model.dto.UserDto;
import com.example.EventsOrganizer.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public List<UserDto> getUsers() {
       return null;
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable String userId) {
        return null;
    }

    @GetMapping("/{userId}/sub/events")
    public EventDto getEventsByUser(@PathVariable String userId) {
        return null;
    }

    @GetMapping("/{userId}/sub/events")
    public ClubDto getClubsByUser(@PathVariable String userId) {
        return null;
    }





}
