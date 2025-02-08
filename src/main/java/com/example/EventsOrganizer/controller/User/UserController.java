package com.example.EventsOrganizer.controller.User;


import com.example.EventsOrganizer.model.dto.ClubDto;
import com.example.EventsOrganizer.model.dto.EventDto;
import com.example.EventsOrganizer.model.dto.UserDto;
import com.example.EventsOrganizer.service.ClubService;
import com.example.EventsOrganizer.service.EventService;
import com.example.EventsOrganizer.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;
    private final ClubService clubService;
    private final EventService eventService;


    @GetMapping()
    public List<UserDto> getUsers(HttpServletRequest request) {

        log.info("Получен запрос вывод списка пользователей {}", request.getRequestURI());
       return userService.findAllUsers();

    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable long userId, HttpServletRequest request) {

        log.info("Получен запрос на вывод информации о пользователе id{} {}", userId, request.getRequestURI());
        return userService.findById(userId);

    }

    @GetMapping("/{userId}/joined-events")
    public List<EventDto> getEventsByUser(@PathVariable long userId, HttpServletRequest request) {

        log.info("Получен запрос на вывод мероприятий, на которые записан пользователь id{} {}", userId, request.getRequestURI());
        return eventService.findAllByUser(userId);

    }

    @GetMapping("/{userId}/subscribed-clubs")
    public List<ClubDto> getClubsByUser(@PathVariable long userId, HttpServletRequest request) {

        log.info("Получен на вывод клубов, на которые подписан пользователь id{} {}", userId, request.getRequestURI());
        return clubService.findAllByUser(userId);

    }


}
