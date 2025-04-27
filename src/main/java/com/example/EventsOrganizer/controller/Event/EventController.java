package com.example.EventsOrganizer.controller.Event;

import com.example.EventsOrganizer.model.dto.event.CreateEventDto;
import com.example.EventsOrganizer.model.dto.event.GetEventDto;
import com.example.EventsOrganizer.model.dto.event.UpdateEventInfoDto;
import com.example.EventsOrganizer.model.dto.user.GetUserForListDto;
import com.example.EventsOrganizer.security.UserPrincipal;
import com.example.EventsOrganizer.service.EventService;
import com.example.EventsOrganizer.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
@Slf4j
public class EventController {

    private final EventService eventService;
    private final UserService userService;


    @PostMapping()
    public ResponseEntity<Void> createEventForOwnClub(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody CreateEventDto createEventDto,
            HttpServletRequest request
    ) {
        log.info("Получен запрос на создание нового мероприятия в клубе пользователем id{} {}", userPrincipal.getUserId(), request.getRequestURI());
        eventService.createEventForOwnClub(userPrincipal.getUserId(), createEventDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("/{eventId}")
    public ResponseEntity<GetEventDto> getEventFromClubById(
            @PathVariable long eventId,
            HttpServletRequest request
    ) {
        log.info("Получен запрос на вывод информации о мероприятии id{} {}", eventId, request.getRequestURI());
        return ResponseEntity.ok(eventService.findEventById(eventId));
    }


    @PatchMapping("{eventId}/join")
    public ResponseEntity<Void> joinToTheEvent(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable long eventId,
            HttpServletRequest request
    ) {
        log.info("Получен запрос на присоединение к мероприятию id{} пользователем id{} {}", eventId, userPrincipal.getUserId(), request.getRequestURI());
        userService.jointToTheEvent(userPrincipal.getUserId(), eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/{eventId}/subscribers")
    public ResponseEntity<Page<GetUserForListDto>> getUsersJoinedToEvent(
            @PathVariable long eventId,
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") @Min(5) @Max(15) int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        log.info("Получен запрос на вывод подписчиков мероприятия id{} {}", eventId, request.getRequestURI());
        return ResponseEntity.ok(userService.findAllByJoinedEventId(eventId, page, size, sortBy, direction));
    }


}
