package com.example.EventsOrganizer.controller.Authentication;


import com.example.EventsOrganizer.model.dto.UserDto;
import com.example.EventsOrganizer.model.entity.User;
import com.example.EventsOrganizer.service.AuthService;
import com.example.EventsOrganizer.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody User user, HttpServletRequest request) {

        log.info("Получен запрос на авторизацию {}", request.getRequestURI());
        return authService.attemptLogin(user.getLogin(), user.getPassword());
    
    }

    @PostMapping("/register")
    public UserDto register(@RequestBody UserDto userDto, HttpServletRequest request) {

        log.info("Получен запрос на регистрацию {}", request.getRequestURI());
        return userService.saveUser(userDto);

    }

}
