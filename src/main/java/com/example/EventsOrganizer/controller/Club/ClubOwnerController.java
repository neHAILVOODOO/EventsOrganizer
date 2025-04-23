package com.example.EventsOrganizer.controller.Club;

import com.example.EventsOrganizer.model.dto.club.CreateClubDto;
import com.example.EventsOrganizer.model.dto.club.UpdateClubDto;
import com.example.EventsOrganizer.model.dto.event.CreateEventDto;
import com.example.EventsOrganizer.model.dto.event.UpdateEventInfoDto;
import com.example.EventsOrganizer.security.UserPrincipal;
import com.example.EventsOrganizer.service.ClubService;
import com.example.EventsOrganizer.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

//ПЕРЕДЕЛАТЬ ТАК, ЧТОБЫ ЭТИ МЕТОДЫ БЫЛИ ВОЗМОЖНЫ И ДЛЯ АДМИНА

@RestController
@RequestMapping("/owner/club")
@RequiredArgsConstructor
@Slf4j
public class ClubOwnerController {

    private final ClubService clubService;
    private final EventService eventService;

    @PostMapping()
    public ResponseEntity<Void> createClub(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody CreateClubDto createClubDto,
            HttpServletRequest request
    ) {
        log.info("Получен запрос на создание нового клуба пользователем id{} {}", userPrincipal.getUserId(), request.getRequestURI());
        clubService.createClub(userPrincipal.getUserId(),createClubDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //и админ тоже
    //удалить userPrincipal и сделать по id в сервисе

    @PatchMapping()
    public ResponseEntity<Void> editClub(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody UpdateClubDto updateClubDto,
            HttpServletRequest request
    ) {

        log.info("Получен запрос на изменение информации о клубе пользователем id{} {}", userPrincipal.getUserId(), request.getRequestURI());
        clubService.updateClub(userPrincipal.getUserId(), updateClubDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //и админ тоже
    //удалить userPrincipal и сделать по id в сервисе

    @DeleteMapping()
    public void deleteClub(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            HttpServletRequest request
    ) {
        log.info("Получен запрос на удаление клуба пользователем id{} {}", userPrincipal.getUserId(), request.getRequestURI());
        clubService.deleteClub(userPrincipal.getUserId());
    }

    //чтобы это делалось по id клуба, а не по клубу пользователя, соответственно переделать код в сервисе
    //удалить userPrincipal и сделать по id в сервисе

    @PostMapping("/events")
    public ResponseEntity<Void> createEventForOwnClub(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody CreateEventDto createEventDto,
            HttpServletRequest request
    ) {
        log.info("Получен запрос на создание нового мероприятия в клубе пользователем id{} {}", userPrincipal.getUserId(), request.getRequestURI());
        eventService.createEventForOwnClub(userPrincipal.getUserId(), createEventDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //и админ тоже (удалить userPrincipal)
    //ну собственно сделать проверку, что либо админ либо хозяин, но это аннотацию создать надо

    @PatchMapping("/events/{eventId}")
    public ResponseEntity<Void> editEvent(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody UpdateEventInfoDto updateEventInfoDto,
            @PathVariable long eventId,
            HttpServletRequest request
    ) {
        log.info("Получен запрос на изменение информации о мероприятии id{} пользователем id{} {}", eventId, userPrincipal.getUserId(), request.getRequestURI());
        eventService.updateEventInfo(updateEventInfoDto, eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //и админ тоже
    //удалить userPrincipal

    @DeleteMapping("/events/{eventId}")
    public void deleteEvent(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable long eventId,
            HttpServletRequest request
    ) {

        log.info("Получен запрос на удаление мероприятия id{} пользователем id{} {}", eventId, userPrincipal.getUserId(), request.getRequestURI());
        eventService.deleteEvent(eventId);

    }


}
