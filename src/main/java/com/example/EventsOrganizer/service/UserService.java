package com.example.EventsOrganizer.service;

import com.example.EventsOrganizer.model.dto.user.CreateUserDto;
import com.example.EventsOrganizer.model.dto.user.GetUserDto;
import com.example.EventsOrganizer.model.dto.user.GetUserForListDto;
import com.example.EventsOrganizer.model.dto.user.UpdateUserBioDto;
import com.example.EventsOrganizer.model.dto.user.UpdateUserLoginDto;
import com.example.EventsOrganizer.model.dto.user.UpdateUserPasswordDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    GetUserDto findById(long userId);

    Page<GetUserForListDto> findAllUsers(int page, int size, String sortBy, String direction);

    void saveUser(CreateUserDto createUserDto);

    Page<GetUserForListDto> findAllBySubscribedClubId(long clubId, int page, int size, String sortBy, String direction);

    Page<GetUserForListDto> findAllByJoinedEventId(long eventId, int page, int size, String sortBy, String direction);

    void updateUserBio(UpdateUserBioDto updateUserBioDto, long userId);
    void updateUserLogin(UpdateUserLoginDto updateUserLoginDto, long userId);
    void updateUserPassword(UpdateUserPasswordDto updateUserPasswordDto, long userId);

    void subscribeToClub(long userId, long clubId);

    void jointToTheEvent(long userId, long eventId);

    void unsubscribeFromClub(long userId, long clubId);

    void leaveTheEvent(long userId, long eventId);

    void deleteUser(long userId);

}
