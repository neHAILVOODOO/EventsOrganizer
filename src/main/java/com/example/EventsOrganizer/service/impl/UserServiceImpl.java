package com.example.EventsOrganizer.service.impl;

import com.example.EventsOrganizer.model.dto.UserDto;
import com.example.EventsOrganizer.model.entity.User;
import com.example.EventsOrganizer.repo.UserRepo;
import com.example.EventsOrganizer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<UserDto> findAllUsers() {
        return null;
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        User user = mapToUser(userDto);
        userRepo.save(user);
        return mapToUserDto(user);
    }


    private User mapToUser(UserDto userDto) {
        User user = User.builder()

                .name(userDto.getName())
                .build();

        return user;
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();

        return userDto;
    }

}
