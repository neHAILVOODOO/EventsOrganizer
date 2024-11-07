package com.example.EventsOrganizer.controller;


import com.example.EventsOrganizer.model.dto.ClubDto;
import com.example.EventsOrganizer.model.dto.EventDto;
import com.example.EventsOrganizer.model.dto.UserDto;
import com.example.EventsOrganizer.security.UserPrincipal;
import com.example.EventsOrganizer.service.ClubService;
import com.example.EventsOrganizer.service.EventService;
import com.example.EventsOrganizer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    UserService userService;
    @Autowired
    ClubService clubService;
    @Autowired
    EventService eventService;

    public ProfileController(UserService userService, ClubService clubService, EventService eventService ) {
        this.userService = userService;
        this.eventService = eventService;
        this.clubService = clubService;
    }

    @GetMapping
    public UserDto getMyProfile(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return userService.findById(userPrincipal.getUserId());
    }

    @PatchMapping
    public UserDto editProfile(@RequestBody UserDto userDto, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return userService.updateUser(userDto, userPrincipal.getUserId());
    }

    @GetMapping("/sub/clubs")
    public List<ClubDto> getSubscribedClubs(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return clubService.findAllByUser(userPrincipal.getUserId());
    }

    @GetMapping("/sub/events")
    public List<EventDto> getJoinedEvents(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return eventService.findAllByUser(userPrincipal.getUserId());
    }

    @PatchMapping("/sub/clubs/{clubId}/unsubscribe")
    public UserDto unsubscribeFromClub(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable long clubId) {
      return userService.unsubscribeFromClub(userPrincipal.getUserId(), clubId);
    }

    @PatchMapping("/sub/events/{eventId}/unsubscribe")
    public UserDto leaveTheEvent(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable long eventId) {
        return userService.leaveTheEvent(userPrincipal.getUserId(), eventId);
    }

}
