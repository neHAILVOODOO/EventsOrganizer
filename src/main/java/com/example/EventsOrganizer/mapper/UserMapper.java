package com.example.EventsOrganizer.mapper;

import com.example.EventsOrganizer.model.dto.user.CreateUserDto;
import com.example.EventsOrganizer.model.entity.Role;
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

}
