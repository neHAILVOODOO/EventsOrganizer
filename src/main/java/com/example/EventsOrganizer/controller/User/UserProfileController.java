package com.example.EventsOrganizer.controller.User;

import com.example.EventsOrganizer.model.dto.club.GetClubForListDto;
import com.example.EventsOrganizer.model.dto.event.GetEventForListDto;
import com.example.EventsOrganizer.model.dto.user.GetUserDto;
import com.example.EventsOrganizer.model.dto.user.UpdateUserBioDto;
import com.example.EventsOrganizer.model.dto.user.UpdateUserLoginDto;
import com.example.EventsOrganizer.model.dto.user.UpdateUserPasswordDto;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


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


    @PatchMapping("/edit/bio")
    public ResponseEntity<String> editUserBio(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody UpdateUserBioDto updateUserBioDto,
            HttpServletRequest request
    ) {

        log.info("Получен запрос на изменение информации о профиле пользователя id{} {}", userPrincipal.getUserId(), request.getRequestURI());
        userService.updateUserBio(updateUserBioDto, userPrincipal.getUserId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PatchMapping("/edit/login")
    public ResponseEntity<String> editUserLogin(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody UpdateUserLoginDto updateUserLoginDto,
            HttpServletRequest request
    ) {

        log.info("Получен запрос на изменение своего логина пользователем id{} {}", userPrincipal.getUserId(), request.getRequestURI());
        userService.updateUserLogin(updateUserLoginDto, userPrincipal.getUserId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PatchMapping("/edit/password")
    public ResponseEntity<String> editUserPassword(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody UpdateUserPasswordDto updateUserPasswordDto,
            HttpServletRequest request
    ) {

        log.info("Получен запрос на изменение своего пароля пользователем id{} {}", userPrincipal.getUserId(), request.getRequestURI());
        userService.updateUserPassword(updateUserPasswordDto, userPrincipal.getUserId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/subscribed-clubs")
    public ResponseEntity<Page<GetClubForListDto>> getSubscribedClubs(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") @Min(5) @Max(15) int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        log.info("Получен запрос на получение информации о своих подписках на клубы пользователем id{} {}", userPrincipal.getUserId(), request.getRequestURI());
        return ResponseEntity.ok(clubService.findAllSubscribedByUser(userPrincipal.getUserId(), page, size, sortBy, direction));
    }


    @GetMapping("/joined-events")
    public ResponseEntity<Page<GetEventForListDto>> getJoinedEvents(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") @Min(5) @Max(15) int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        log.info("Получен запрос на получение информации о своих записях на мероприятия пользователем id{} {}", userPrincipal.getUserId(), request.getRequestURI());
        return ResponseEntity.ok(eventService.findAllJoinedByUser(userPrincipal.getUserId(), page, size, sortBy, direction));
    }


    @PatchMapping("/subscribed-clubs/{clubId}")
    public ResponseEntity<Void> unsubscribeFromClub(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable long clubId, HttpServletRequest request) {

        log.info("Получен запрос на отписку от клуба id{} пользователем id{} {}", clubId, userPrincipal.getUserId(), request.getRequestURI());
        userService.unsubscribeFromClub(userPrincipal.getUserId(), clubId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PatchMapping("/joined-events/{eventId}")
    public ResponseEntity<Void> leaveTheEvent(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable long eventId, HttpServletRequest request) {

        log.info("Получен запрос на отмену посещения мероприятия id{} пользователем id{} {}", eventId, userPrincipal.getUserId(), request.getRequestURI());
        userService.leaveTheEvent(userPrincipal.getUserId(), eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}
