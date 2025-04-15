package com.example.EventsOrganizer.controller.User;

import com.example.EventsOrganizer.model.dto.ClubDto;
import com.example.EventsOrganizer.model.dto.EventDto;
import com.example.EventsOrganizer.model.dto.UserDto;
import com.example.EventsOrganizer.model.dto.user.GetUserDto;
import com.example.EventsOrganizer.security.UserPrincipal;
import com.example.EventsOrganizer.service.ClubService;
import com.example.EventsOrganizer.service.EventService;
import com.example.EventsOrganizer.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
    public ResponseEntity<GetUserDto> getMyProfile(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            HttpServletRequest request
    ) {
        log.info("Получен запрос на получение информации о своем профиле пользователем id{} {}", userPrincipal.getUserId(), request.getRequestURI());
        return ResponseEntity.ok(userService.findById(userPrincipal.getUserId()));
    }

    //дто
    //респонсэнтити
    //валидация
    //разделить метод изменения данных на 3 -> пароль, логин и био

    @PatchMapping
    public ResponseEntity<String> editProfile(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody UserDto userDto,
            HttpServletRequest request
    ) {

        log.info("Получен запрос на изменение информации о своем профиле пользователем id{} {}", userPrincipal.getUserId(), request.getRequestURI());
        userService.updateUser(userDto, userPrincipal.getUserId());

    }

    //дто
    //респонсэнтити

    @GetMapping("/subscribed-clubs")
    public Page<ClubDto> getSubscribedClubs(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") @Min(5) @Max(15) int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        log.info("Получен запрос на получение информации о своих подписках на клубы пользователем id{} {}", userPrincipal.getUserId(), request.getRequestURI());
        return clubService.findAllByUser(userPrincipal.getUserId());
    }

    //дто
    //респонсэнтити

    @GetMapping("/joined-events")
    public List<EventDto> getJoinedEvents(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            HttpServletRequest request
    ) {
        log.info("Получен запрос на получение информации о своих записях на мероприятия пользователем id{} {}", userPrincipal.getUserId(), request.getRequestURI());
        return eventService.findAllByUser(userPrincipal.getUserId());
    }

    //дто
    //респонсэнтити
    //валидация

    @PatchMapping("/subscribed-clubs/{clubId}")
    public UserDto unsubscribeFromClub(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable long clubId, HttpServletRequest request) {

        log.info("Получен запрос на отписку от клуба id{} пользователем id{} {}", clubId, userPrincipal.getUserId(), request.getRequestURI());
        return userService.unsubscribeFromClub(userPrincipal.getUserId(), clubId);

    }

    //дто
    //респонсэнтити
    //валидация

    @PatchMapping("/joined-events/{eventId}")
    public UserDto leaveTheEvent(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable long eventId, HttpServletRequest request) {

        log.info("Получен запрос на отмену посещения мероприятия id{} пользователем id{} {}", eventId, userPrincipal.getUserId(), request.getRequestURI());
        return userService.leaveTheEvent(userPrincipal.getUserId(), eventId);

    }

}
