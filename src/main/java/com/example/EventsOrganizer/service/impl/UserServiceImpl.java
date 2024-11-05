package com.example.EventsOrganizer.service.impl;

import com.example.EventsOrganizer.model.dto.UserDto;
import com.example.EventsOrganizer.model.entity.Club;
import com.example.EventsOrganizer.model.entity.Event;
import com.example.EventsOrganizer.model.entity.User;
import com.example.EventsOrganizer.repo.ClubRepo;
import com.example.EventsOrganizer.repo.EventRepo;
import com.example.EventsOrganizer.repo.UserRepo;
import com.example.EventsOrganizer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ClubRepo clubRepo;
    @Autowired
    private EventRepo eventRepo;

    public UserServiceImpl(UserRepo userRepo, ClubRepo clubRepo, EventRepo eventRepo) {
        this.userRepo = userRepo;
        this.clubRepo = clubRepo;
        this.eventRepo = eventRepo;
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

    @Override
    public List<UserDto> findAllByJoinedEventId(long eventId) {
        List<User> users = userRepo.findAllByJoinedEventId(eventId);
        return users.stream().map((user) -> mapToUserDto(user)).collect(Collectors.toList());
    }


    @Transactional
    @Override
    public UserDto subscribeToClub(long userId, long clubId) {
        User user = userRepo.findUserById(userId);
        Club club = clubRepo.findClubById(clubId);

        if (club.getUsers().contains(user)) {

            throw new IllegalStateException("Пользователь уже подписан на этот клуб.");

        } else  {
            user.getSubscribedClubs().add(club);
            userRepo.save(user);
            return mapToUserDto(user);
        }




    }

    @Transactional
    @Override
    public UserDto jointToTheEvent(long userId, long eventId, long clubId) {
       User user = userRepo.findUserById(userId);
       Event event = eventRepo.findByOrganizingClub_IdAndId(clubId, eventId);

       if (event.getJoinedUsers().contains(user)) {

           throw new IllegalStateException("Пользователь уже присоединился к этому событию.");

       } else {
           user.getJoinedEvents().add(event);
           userRepo.save(user);
           return mapToUserDto(user);
       }
    }

//    @Override
//    public UserDto updateUser(UserDto userDto, long userId) {
//        User user = userRepo.findUserById(userId);
//
//        String
//
//        return null;
//    }


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
