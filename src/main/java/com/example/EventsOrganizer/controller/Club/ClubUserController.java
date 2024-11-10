package com.example.EventsOrganizer.controller.Club;


import com.example.EventsOrganizer.model.dto.ClubDto;
import com.example.EventsOrganizer.model.dto.EventDto;
import com.example.EventsOrganizer.model.dto.UserDto;
import com.example.EventsOrganizer.security.UserPrincipal;
import com.example.EventsOrganizer.service.ClubService;
import com.example.EventsOrganizer.service.EventService;
import com.example.EventsOrganizer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clubs")
public class ClubUserController {

    private final ClubService clubService;
    private final EventService eventService;
    private final UserService userService;

    @GetMapping()
    public List<ClubDto> getAllClubs() {
        return clubService.getAllClubs();
    }

    @GetMapping("/{clubId}")
    public ClubDto getClub(@PathVariable long clubId) {
        return clubService.findClubById(clubId);
    }

    @GetMapping("/{clubId}/subscribers")
    public List<UserDto> getClubSubscribers(@PathVariable long clubId) {
        return userService.findAllBySubscribedClubId(clubId);
    }

    @PatchMapping("/{clubId}/subscribe")
    public UserDto subscribeToClub(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable long clubId) {
        return userService.subscribeToClub(userPrincipal.getUserId(), clubId);
    }

    @GetMapping("/{clubId}/events")
    public List<EventDto> getEventsFromClub(@PathVariable long clubId) {
        return eventService.findEventsByClub(clubId);
    }

    @GetMapping("/{clubId}/events/{eventId}")
    public EventDto getEventFromClubById(@PathVariable long clubId, @PathVariable long eventId) {
        return eventService.findEventByClubAndEventId(clubId, eventId);
    }

    @PatchMapping("/{clubId}/events/{eventId}/join")
    public UserDto joinToTheEvent(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable long eventId, @PathVariable long clubId) {
        return userService.jointToTheEvent(userPrincipal.getUserId(), eventId, clubId);
    }

    @GetMapping("/{clubId}/events/{eventId}/subscribers")
    public List<UserDto> getUsersJoinedToEvent(@PathVariable long eventId) {
        return userService.findAllByJoinedEventId(eventId);
    }




}
