package com.example.EventsOrganizer.controller.User;

import com.example.EventsOrganizer.model.dto.club.GetClubForListDto;
import com.example.EventsOrganizer.model.dto.event.GetEventForListDto;
import com.example.EventsOrganizer.model.dto.user.GetUserDto;
import com.example.EventsOrganizer.model.dto.user.GetUserForListDto;
import com.example.EventsOrganizer.service.ClubService;
import com.example.EventsOrganizer.service.EventService;
import com.example.EventsOrganizer.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    private final UserService userService;
    private final ClubService clubService;
    private final EventService eventService;

    @GetMapping()
    public ResponseEntity<Page<GetUserForListDto>> getUsers(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") @Min(5) @Max(15) int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        log.info("Получен запрос вывод списка пользователей {}", request.getRequestURI());
        return ResponseEntity.ok(userService.findAllUsers(page, size, sortBy, direction));
    }


    @GetMapping("/{userId}")
    public ResponseEntity<GetUserDto> getUser(
            @PathVariable long userId,
            HttpServletRequest request
    ) {
        log.info("Получен запрос на вывод информации о пользователе id{} {}", userId, request.getRequestURI());
        return ResponseEntity.ok(userService.findById(userId));
    }

    //Чтобы и админ тоже мог удалять пользователя
    
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable long userId, HttpServletRequest request) {

        log.info("Получен запрос на удаление профиля пользователя id{} {}", userId, request.getRequestURI());
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


    @GetMapping("/{userId}/joined-events")
    public ResponseEntity<Page<GetEventForListDto>> getEventsByUser(
            @PathVariable long userId,
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") @Min(5) @Max(15) int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        log.info("Получен запрос на вывод мероприятий, на которые записан пользователь id{} {}", userId, request.getRequestURI());
        return ResponseEntity.ok(eventService.findAllJoinedByUser(userId, page, size, sortBy, direction));
    }


    @GetMapping("/{userId}/subscribed-clubs")
    public ResponseEntity<Page<GetClubForListDto>> getClubsByUser(
            @PathVariable long userId,
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") @Min(5) @Max(15) int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        log.info("Получен на вывод клубов, на которые подписан пользователь id{} {}", userId, request.getRequestURI());
        return ResponseEntity.ok(clubService.findAllSubscribedByUser(userId, page, size, sortBy, direction));
    }


}
