package com.example.EventsOrganizer.controller.User;


import com.example.EventsOrganizer.model.dto.ClubDto;
import com.example.EventsOrganizer.model.dto.EventDto;
import com.example.EventsOrganizer.model.dto.UserDto;
import com.example.EventsOrganizer.security.UserPrincipal;
import com.example.EventsOrganizer.service.ClubService;
import com.example.EventsOrganizer.service.EventService;
import com.example.EventsOrganizer.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
@Slf4j
public class UserProfileController {

    private final UserService userService;
    private final ClubService clubService;
    private final EventService eventService;

    @GetMapping
    public UserDto getMyProfile(@AuthenticationPrincipal UserPrincipal userPrincipal, HttpServletRequest request) {

        log.info("Получен запрос на получение информации о своем профиле пользователем id{} {}", userPrincipal.getUserId(), request.getRequestURI());
        return userService.findById(userPrincipal.getUserId());

    }

    @PatchMapping
    public UserDto editProfile(@AuthenticationPrincipal UserPrincipal userPrincipal,@RequestBody UserDto userDto, HttpServletRequest request) {

        log.info("Получен запрос на изменение информации о своем профиле пользователем id{} {}", userPrincipal.getUserId(), request.getRequestURI());
        return userService.updateUser(userDto, userPrincipal.getUserId());

    }

    @GetMapping("/subscribed-clubs")
    public List<ClubDto> getSubscribedClubs(@AuthenticationPrincipal UserPrincipal userPrincipal, HttpServletRequest request) {

        log.info("Получен запрос на получение информации о своих подписках на клубы пользователем id{} {}", userPrincipal.getUserId(), request.getRequestURI());
        return clubService.findAllByUser(userPrincipal.getUserId());

    }

    @GetMapping("/joined-events")
    public List<EventDto> getJoinedEvents(@AuthenticationPrincipal UserPrincipal userPrincipal, HttpServletRequest request) {

        log.info("Получен запрос на получение информации о своих записях на мероприятия пользователем id{} {}", userPrincipal.getUserId(), request.getRequestURI());
        return eventService.findAllByUser(userPrincipal.getUserId());

    }

    @PatchMapping("/subscribed-clubs/{clubId}")
    public UserDto unsubscribeFromClub(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable long clubId, HttpServletRequest request) {

        log.info("Получен запрос на отписку от клуба id{} пользователем id{} {}", clubId, userPrincipal.getUserId(), request.getRequestURI());
        return userService.unsubscribeFromClub(userPrincipal.getUserId(), clubId);

    }

    @PatchMapping("/joined-events/{eventId}")
    public UserDto leaveTheEvent(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable long eventId, HttpServletRequest request) {

        log.info("Получен запрос на отмену посещения мероприятия id{} пользователем id{} {}", eventId, userPrincipal.getUserId(), request.getRequestURI());
        return userService.leaveTheEvent(userPrincipal.getUserId(), eventId);

    }

}
