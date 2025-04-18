package com.example.EventsOrganizer.mapper;

import com.example.EventsOrganizer.model.dto.user.CreateUserDto;
import com.example.EventsOrganizer.model.dto.user.GetUserDto;
import com.example.EventsOrganizer.model.dto.user.GetUserForListDto;
import com.example.EventsOrganizer.model.dto.user.UserPreviewDto;
import com.example.EventsOrganizer.model.enums.Role;
import com.example.EventsOrganizer.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public User mapCreateUserDtoToUser(CreateUserDto createUserDto) {

        return User.builder()
                .login(createUserDto.getLogin())
                .password(createUserDto.getPassword())
                .name(createUserDto.getName())
                .roles(List.of(Role.USER))
                .build();
    }

    public GetUserForListDto mapUserToGetUserForListDto(User user) {

        return GetUserForListDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .build();
    }

    public GetUserDto mapUserToGetUserDto(User user) {

        return GetUserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .phoneNumber(user.getPhoneNumber())
                .age(user.getAge())
                .roles(user.getRoles())
                .build();

    }

    public UserPreviewDto mapUserToUserPreviewDto(User user) {

        return UserPreviewDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .phoneNumber(user.getPhoneNumber())
                .build();

    }

}
