package com.example.EventsOrganizer.service;

import com.example.EventsOrganizer.model.dto.user.CreateUserDto;
import com.example.EventsOrganizer.model.dto.user.GetUserDto;
import com.example.EventsOrganizer.model.dto.user.GetUserForListDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    GetUserDto findById(long userId);

    Page<GetUserForListDto> findAllUsers(int page, int size, String sortBy, String direction);

    void saveUser(CreateUserDto createUserDto);

    List<UserDto> findAllBySubscribedClubId(long clubId);

    List<UserDto> findAllByJoinedEventId(long eventId);

    UserDto updateUser(UserDto userDto, long userId);

    UserDto subscribeToClub(long userId, long clubId);

    UserDto jointToTheEvent(long userId, long eventId,  long clubId);

    UserDto unsubscribeFromClub(long userId, long clubId);

    UserDto leaveTheEvent(long userId, long eventId);

    void deleteUser(long userId);

}
