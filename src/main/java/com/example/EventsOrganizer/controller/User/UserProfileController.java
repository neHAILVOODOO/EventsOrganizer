package com.example.EventsOrganizer.controller.User;


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
@RequestMapping("/profile")
public class UserProfileController {

    private final UserService userService;
    private final ClubService clubService;
    private final EventService eventService;

    @GetMapping
    public UserDto getMyProfile(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return userService.findById(userPrincipal.getUserId());
    }

    @PatchMapping
    public UserDto editProfile(@AuthenticationPrincipal UserPrincipal userPrincipal,@RequestBody UserDto userDto) {
        return userService.updateUser(userDto, userPrincipal.getUserId());
    }

    @GetMapping("/subscribed-clubs")
    public List<ClubDto> getSubscribedClubs(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return clubService.findAllByUser(userPrincipal.getUserId());
    }

    @GetMapping("/joined-events")
    public List<EventDto> getJoinedEvents(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return eventService.findAllByUser(userPrincipal.getUserId());
    }

    @PatchMapping("/subscribed-clubs/{clubId}")
    public UserDto unsubscribeFromClub(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable long clubId) {
      return userService.unsubscribeFromClub(userPrincipal.getUserId(), clubId);
    }

    @PatchMapping("/joined-events/{eventId}")
    public UserDto leaveTheEvent(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable long eventId) {
        return userService.leaveTheEvent(userPrincipal.getUserId(), eventId);
    }

}
