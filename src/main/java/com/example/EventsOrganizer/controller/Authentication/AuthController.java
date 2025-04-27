package com.example.EventsOrganizer.controller.Authentication;


import com.example.EventsOrganizer.model.dto.user.CreateUserDto;
import com.example.EventsOrganizer.model.dto.user.UserLoginDto;
import com.example.EventsOrganizer.service.AuthService;
import com.example.EventsOrganizer.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private final AuthService authServiceImpl;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody UserLoginDto userLoginDto,
            HttpServletRequest request
    ) {
        log.info("Получен запрос на авторизацию {}", request.getRequestURI());
        String token = authServiceImpl.attemptLogin(userLoginDto.getLogin(), userLoginDto.getPassword());
        return ResponseEntity.ok(token);
    }


    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @Valid @RequestBody CreateUserDto user,
            HttpServletRequest request
    ) {
        log.info("Получен запрос на регистрацию {}", request.getRequestURI());
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }


}
