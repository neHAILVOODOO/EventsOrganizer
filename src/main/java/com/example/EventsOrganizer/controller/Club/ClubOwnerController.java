package com.example.EventsOrganizer.controller.Club;


import com.example.EventsOrganizer.model.dto.ClubDto;
import com.example.EventsOrganizer.model.dto.EventDto;
import com.example.EventsOrganizer.security.UserPrincipal;
import com.example.EventsOrganizer.service.ClubService;
import com.example.EventsOrganizer.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owner/club")
@RequiredArgsConstructor
public class ClubOwnerController {

    private final ClubService clubService;
    private final EventService eventService;

    @PostMapping()
    public ClubDto createClub(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody ClubDto clubDto) {
        return clubService.saveClub(userPrincipal.getUserId(),clubDto);
    }

    @PatchMapping()
    public ClubDto editClub(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody ClubDto clubDto) {
        return clubService.updateOwnerClub(userPrincipal.getUserId(),clubDto);
    }

    @DeleteMapping()
    public void deleteClub(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        clubService.deleteOwnClub(userPrincipal.getUserId());
    }

    @PostMapping("/events")
    public EventDto createEventForOwnClub(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody EventDto eventDto) {
        return eventService.createEventForOwnClub(userPrincipal.getUserId(), eventDto);
    }

    @PatchMapping("/events/{eventId}")
    public EventDto editEvent(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody EventDto eventDto, @PathVariable long eventId) {
        return eventService.updateEvent(userPrincipal.getUserId(),eventDto, eventId);
    }

    @DeleteMapping("/events/{eventId}")
    public void deleteEvent(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable long eventId) {
        eventService.deleteEvent(userPrincipal.getUserId(), eventId);
    }


}
