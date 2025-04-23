package com.example.EventsOrganizer.controller.User;


import com.example.EventsOrganizer.model.dto.user.UpdateUserBioDto;
import com.example.EventsOrganizer.model.dto.user.UpdateUserLoginDto;
import com.example.EventsOrganizer.model.dto.user.UpdateUserPasswordDto;
import com.example.EventsOrganizer.security.UserPrincipal;
import com.example.EventsOrganizer.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
@Slf4j
public class UserProfileController {

    private final UserService userService;


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


    //короче вот эти два метода перенести в club и event соответственно и сделать уникальными для каждой роли (овнер клуба, админ и юзер)

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
