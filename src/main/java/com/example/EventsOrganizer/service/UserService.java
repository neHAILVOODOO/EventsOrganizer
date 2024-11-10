package com.example.EventsOrganizer.service;

import com.example.EventsOrganizer.model.dto.UserDto;
import com.example.EventsOrganizer.model.entity.User;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserService {

    UserDto findById(long userId);

    List<UserDto> findAllUsers();

    UserDto saveUser(UserDto userDto);

    List<UserDto> findAllBySubscribedClubId(long clubId);

    List<UserDto> findAllByJoinedEventId(long eventId);

    UserDto updateUser(UserDto userDto, long userId);

    UserDto subscribeToClub(long userId, long clubId);

    UserDto jointToTheEvent(long userId, long eventId,  long clubId);

    UserDto unsubscribeFromClub(long userId, long clubId);

    UserDto leaveTheEvent(long userId, long eventId);

    void deleteUser(long userId);

}
