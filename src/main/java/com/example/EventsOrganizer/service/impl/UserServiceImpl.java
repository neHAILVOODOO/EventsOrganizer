package com.example.EventsOrganizer.service.impl;

import com.example.EventsOrganizer.model.dto.UserDto;
import com.example.EventsOrganizer.model.entity.User;
import com.example.EventsOrganizer.repo.UserRepo;
import com.example.EventsOrganizer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepo userRepo;

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

    @Override
    public List<UserDto> findAllBySubscribedClubId(long clubId) {
        List<User> users = userRepo.findAllBySubscribedClubId(clubId);
        return users.stream().map((user) -> mapToUserDto(user)).collect(Collectors.toList());
    }


    private User mapToUser(UserDto userDto) {
        User user = User.builder()

                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .phoneNumber(userDto.getPhoneNumber())
                .age(userDto.getAge())
                .subscribedClubs(userDto.getSubscribedClubs())
                .ownClub(userDto.getOwnClub())
                .joinedEvents(userDto.getJoinedEvents())


                .build();

        return user;
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .name(user.getName())
                .surname(user.getSurname())
                .phoneNumber(user.getPhoneNumber())
                .age(user.getAge())
                .subscribedClubs(user.getSubscribedClubs())
                .ownClub(user.getOwnClub())
                .joinedEvents(user.getJoinedEvents())
                .build();

        return userDto;
    }

}
