package com.example.EventsOrganizer.controller.Club;

import com.example.EventsOrganizer.model.dto.club.GetClubDto;
import com.example.EventsOrganizer.model.dto.club.GetClubForListDto;
import com.example.EventsOrganizer.model.dto.event.GetEventDto;
import com.example.EventsOrganizer.model.dto.event.GetEventForListDto;
import com.example.EventsOrganizer.model.dto.user.GetUserForListDto;
import com.example.EventsOrganizer.security.UserPrincipal;
import com.example.EventsOrganizer.service.ClubService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clubs")
@Slf4j
public class ClubUserController {

    private final ClubService clubService;
    private final EventService eventService;
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<Page<GetClubForListDto>> getAllClubs(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") @Min(5) @Max(15) int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        log.info("Получен запрос на вывод всех клубов {}", request.getRequestURI());
        return ResponseEntity.ok(clubService.getAllClubs(page, size, sortBy, direction));
    }

    @GetMapping("/{clubId}")
    public ResponseEntity<GetClubDto> getClub(
            @PathVariable long clubId,
            HttpServletRequest request
    ) {
        log.info("Получен запрос на вывод информации о клубе id{} {}", clubId, request.getRequestURI());
        return ResponseEntity.ok(clubService.findClubById(clubId));
    }

    @GetMapping("/{clubId}/subscribers")
    public ResponseEntity<Page<GetUserForListDto>> getClubSubscribers(
            @PathVariable long clubId,
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") @Min(5) @Max(15) int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {

        log.info("Получен запрос на вывод подписчиков клуба id{} {}", clubId, request.getRequestURI());
        return ResponseEntity.ok(userService.findAllBySubscribedClubId(clubId,page,size,sortBy,direction));

    }

    @PatchMapping("/{clubId}/subscribe")
    public ResponseEntity<Void> subscribeToClub(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable long clubId, HttpServletRequest request
    ) {
        log.info("Получен запрос на подписку на клуб id{} пользователем id{} {}", clubId, userPrincipal.getUserId(), request.getRequestURI());
        userService.subscribeToClub(userPrincipal.getUserId(), clubId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{clubId}/events")
    public ResponseEntity<Page<GetEventForListDto>> getEventsFromClub(
            @PathVariable long clubId,
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") @Min(5) @Max(15) int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {

        log.info("Получен запрос на вывод мероприятий клуба id{} {}", clubId, request.getRequestURI());
        return ResponseEntity.ok(eventService.findEventsByClub(clubId, page, size, sortBy, direction));

    }

    //Помеченные методы ниже отдельно перенести в eventscontroller, а маппинг у этого метода просто заменить на /{eventId}

    @GetMapping("/{clubId}/events/{eventId}")
    public ResponseEntity<GetEventDto> getEventFromClubById(
            @PathVariable long clubId,
            @PathVariable long eventId,
            HttpServletRequest request
    ) {
        log.info("Получен запрос на вывод информации о мероприятии id{} {}", eventId, request.getRequestURI());
        return ResponseEntity.ok(eventService.findEventById(eventId));
    }

    // этот

    @PatchMapping("/{clubId}/events/{eventId}/join")
    public ResponseEntity<Void> joinToTheEvent(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable long eventId,
            @PathVariable long clubId,
            HttpServletRequest request
    ) {

        log.info("Получен запрос на присоединение к мероприятию id{} пользователем id{} {}", eventId, userPrincipal.getUserId(), request.getRequestURI());
        userService.jointToTheEvent(userPrincipal.getUserId(), eventId, clubId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // этот

    @GetMapping("/{clubId}/events/{eventId}/subscribers")
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
