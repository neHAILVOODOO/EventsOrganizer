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

    @PostMapping()
    public ClubDto createClub(@RequestBody ClubDto clubDto) {
        return clubService.saveClub(clubDto);
    }

    @GetMapping("/{clubId}")
    public ClubDto getClub(@PathVariable long clubId) {
        return clubService.findClubById(clubId);
    }

    @PatchMapping("/{clubId}")
    public ClubDto editClub(@RequestBody ClubDto clubDto, @PathVariable long clubId) {
        return clubService.updateClub(clubDto,clubId);
    }

    @DeleteMapping("/{clubId}")
    public void deleteClub(@PathVariable long clubId) {
        clubService.deleteClub(clubId);
    }

    @GetMapping("/{clubId}/subscribers")
    public List<UserDto> getClubSubscribers(@PathVariable long clubId) {
        return userService.findAllBySubscribedClubId(clubId);
    }

    @PatchMapping("/{clubId}/subscribe")
    public UserDto subscribeToClub(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable long clubId) {
        long userId = userPrincipal.getUserId();
        return userService.subscribeToClub(userId, clubId);
    }

    @GetMapping("/{clubId}/events")
    public List<EventDto> getEventsFromClub(@PathVariable long clubId) {
        return eventService.findEventsByClub(clubId);
    }

    @PostMapping("/{clubId}/events")
    public EventDto createEventForClub(@RequestBody EventDto eventDto, @PathVariable long clubId) {
        return eventService.createEventForClub(eventDto, clubId);
    }

    @GetMapping("/{clubId}/events/{eventId}")
    public EventDto getEventFromClubById(@PathVariable long clubId, @PathVariable long eventId) {
        return eventService.findEventByClubAndEventId(clubId, eventId);
    }

    @PatchMapping("/{clubId}/events/{eventId}")
    public EventDto editEvent(@RequestBody EventDto eventDto, @PathVariable long clubId, @PathVariable long eventId) {
        return eventService.updateEvent(eventDto,clubId,eventId);
    }

    @DeleteMapping("/{clubId}/events/{eventId}")
    public void deleteEvent(@PathVariable long clubId, @PathVariable long eventId) {
        eventService.deleteEvent(clubId, eventId);
    }

    @PatchMapping("/{clubId}/events/{eventId}/join")
    public UserDto joinToTheEvent(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable long eventId, @PathVariable long clubId) {
        long userId = userPrincipal.getUserId();
        return userService.jointToTheEvent(userId, eventId, clubId);
    }


    @GetMapping("/{clubId}/events/{eventId}/subscribers")
    public List<UserDto> getUsersJoinedToEvent(@PathVariable long eventId) {
        return userService.findAllByJoinedEventId(eventId);
    }




}
