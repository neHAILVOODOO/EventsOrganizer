package com.example.EventsOrganizer.controller.Club;


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
@RequestMapping("/clubs")
@Slf4j
public class ClubUserController {

    private final ClubService clubService;
    private final EventService eventService;
    private final UserService userService;

    @GetMapping()
    public List<ClubDto> getAllClubs(HttpServletRequest request) {

        log.info("Получен запрос на вывод всех клубов {}", request.getRequestURI());
        return clubService.getAllClubs();

    }

    @GetMapping("/{clubId}")
    public ClubDto getClub(@PathVariable long clubId, HttpServletRequest request) {

        log.info("Получен запрос на вывод информации о клубе id{} {}", clubId, request.getRequestURI());
        return clubService.findClubById(clubId);

    }

    @GetMapping("/{clubId}/subscribers")
    public List<UserDto> getClubSubscribers(@PathVariable long clubId, HttpServletRequest request) {

        log.info("Получен запрос на вывод подписчиков клуба id{} {}", clubId, request.getRequestURI());
        return userService.findAllBySubscribedClubId(clubId);

    }

    @PatchMapping("/{clubId}/subscribe")
    public UserDto subscribeToClub(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable long clubId, HttpServletRequest request) {

        log.info("Получен запрос на подписку на клуб id{} пользователем id{} {}", clubId, userPrincipal.getUserId(), request.getRequestURI());
        return userService.subscribeToClub(userPrincipal.getUserId(), clubId);

    }

    @GetMapping("/{clubId}/events")
    public List<EventDto> getEventsFromClub(@PathVariable long clubId, HttpServletRequest request) {

        log.info("Получен запрос на вывод мероприятий клуба id{} {}", clubId, request.getRequestURI());
        return eventService.findEventsByClub(clubId);

    }

    @GetMapping("/{clubId}/events/{eventId}")
    public EventDto getEventFromClubById(@PathVariable long clubId, @PathVariable long eventId, HttpServletRequest request) {

        log.info("Получен запрос на вывод информации о мероприятии id{} {}", eventId, request.getRequestURI());
        return eventService.findEventById(eventId);

    }

    @PatchMapping("/{clubId}/events/{eventId}/join")
    public UserDto joinToTheEvent(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable long eventId, @PathVariable long clubId, HttpServletRequest request) {

        log.info("Получен запрос на присоединение к мероприятию id{} пользователем id{} {}", eventId, userPrincipal.getUserId(), request.getRequestURI());
        return userService.jointToTheEvent(userPrincipal.getUserId(), eventId, clubId);

    }

    @GetMapping("/{clubId}/events/{eventId}/subscribers")
    public List<UserDto> getUsersJoinedToEvent(@PathVariable long eventId, HttpServletRequest request) {

        log.info("Получен запрос на вывод подписчиков мероприятия id{} {}", eventId, request.getRequestURI());
        return userService.findAllByJoinedEventId(eventId);

    }




}
