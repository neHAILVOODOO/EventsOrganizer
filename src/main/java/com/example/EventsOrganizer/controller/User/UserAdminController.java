package com.example.EventsOrganizer.controller.User;


import com.example.EventsOrganizer.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
@Slf4j
public class UserAdminController {

    private final UserService userService;

    @PatchMapping("/{userId}")
    public UserDto editUser(@RequestBody UserDto userDto, @PathVariable long userId, HttpServletRequest request) {

        log.info("Получен запрос на изменение профиля пользователя id{} {}", userId, request.getRequestURI());
        return userService.updateUser(userDto, userId);

    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId, HttpServletRequest request) {

        log.info("Получен запрос на удаление профиля пользователя id{} {}", userId, request.getRequestURI());
        userService.deleteUser(userId);

    }

}
