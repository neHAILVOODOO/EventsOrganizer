package com.example.EventsOrganizer.controller;


import com.example.EventsOrganizer.model.dto.ClubDto;
import com.example.EventsOrganizer.model.dto.EventDto;
import com.example.EventsOrganizer.model.dto.UserDto;
import com.example.EventsOrganizer.service.ClubService;
import com.example.EventsOrganizer.service.EventService;
import com.example.EventsOrganizer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clubs")
public class ClubController {

    @Autowired
    ClubService clubService;
    @Autowired
    EventService eventService;
    @Autowired
    UserService userService;

    public ClubController(ClubService clubService, EventService eventService, UserService userService) {
        this.clubService = clubService;
        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping()
    public List<ClubDto> getAllClubs() {
        return clubService.getAllClubs();
    }

    @PostMapping("/create")
    public ClubDto createClub(@RequestBody ClubDto clubDto) {
        return clubService.saveClub(clubDto);
    }

    @GetMapping("/{clubId}")
    public ClubDto getClub(@PathVariable long clubId) {
        return clubService.findClubById(clubId);
    }

    @GetMapping("/{clubId}/events")
    public List<EventDto> getEventsFromClub(@PathVariable long clubId) {
        return eventService.getEventsFromClub(clubId);
    }

    @PostMapping("/{clubId}/events/create")
    public EventDto createEventForClub(@RequestBody EventDto eventDto, @PathVariable long clubId) {
        return eventService.createEventForClub(eventDto, clubId);
    }

    @GetMapping("/{clubId}/subscribers")
    public List<UserDto> getClubSubscribers(@PathVariable long clubId) {
        System.out.println("попка");
        return userService.findAllBySubscribedClubId(clubId);
    }


}
