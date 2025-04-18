package com.example.EventsOrganizer.controller.Club;


import com.example.EventsOrganizer.model.dto.event.CreateEventDto;
import com.example.EventsOrganizer.security.UserPrincipal;
import com.example.EventsOrganizer.service.ClubService;
import com.example.EventsOrganizer.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/owner/club")
@RequiredArgsConstructor
@Slf4j
public class ClubOwnerController {

    private final ClubService clubService;
    private final EventService eventService;

    @PostMapping()
    public ClubDto createClub(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody ClubDto clubDto, HttpServletRequest request) {

        log.info("Получен запрос на создание нового клуба пользователем id{} {}", userPrincipal.getUserId(), request.getRequestURI());
        return clubService.saveClub(userPrincipal.getUserId(),clubDto);

    }

    @PatchMapping()
    public ClubDto editClub(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody ClubDto clubDto, HttpServletRequest request) {

        log.info("Получен запрос на изменение информации о клубе пользователем id{} {}", userPrincipal.getUserId(), request.getRequestURI());
        return clubService.updateOwnerClub(userPrincipal.getUserId(),clubDto);

    }

    @DeleteMapping()
    public void deleteClub(@AuthenticationPrincipal UserPrincipal userPrincipal, HttpServletRequest request) {

        log.info("Получен запрос на удаление клуба пользователем id{} {}", userPrincipal.getUserId(), request.getRequestURI());
        clubService.deleteClub(userPrincipal.getUserId());

    }

    @PostMapping("/events")
    public EventDto createEventForOwnClub(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody CreateEventDto createEventDto, HttpServletRequest request) {

        log.info("Получен запрос на создание нового мероприятия в клубе пользователем id{} {}", userPrincipal.getUserId(), request.getRequestURI());
        eventService.createEventForOwnClub(userPrincipal.getUserId(), eventDto);
        return null;
    }

    @PatchMapping("/events/{eventId}")
    public EventDto editEvent(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody EventDto eventDto, @PathVariable long eventId, HttpServletRequest request) {

        log.info("Получен запрос на изменение информации о мероприятии id{} пользователем id{} {}", eventId, userPrincipal.getUserId(), request.getRequestURI());
        return eventService.updateEventInfo(userPrincipal.getUserId(), eventId);

    }

    @DeleteMapping("/events/{eventId}")
    public void deleteEvent(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable long eventId, HttpServletRequest request) {

        log.info("Получен запрос на удаление мероприятия id{} пользователем id{} {}", eventId, userPrincipal.getUserId(), request.getRequestURI());
        eventService.deleteEvent(userPrincipal.getUserId(), eventId);

    }


}
