package com.example.EventsOrganizer.service;

import com.example.EventsOrganizer.model.dto.UserDto;
import com.example.EventsOrganizer.model.entity.User;

import java.util.List;

public interface UserService {

    List<UserDto> findAllUsers();

    UserDto saveUser(UserDto userDto);

    List<UserDto> findAllBySubscribedClubId(long clubId);

}
