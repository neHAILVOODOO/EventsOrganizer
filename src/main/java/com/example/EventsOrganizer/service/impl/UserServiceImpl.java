package com.example.EventsOrganizer.service.impl;

import com.example.EventsOrganizer.model.dto.UserDto;
import com.example.EventsOrganizer.model.entity.Club;
import com.example.EventsOrganizer.model.entity.Event;
import com.example.EventsOrganizer.model.entity.Role;
import com.example.EventsOrganizer.model.entity.User;
import com.example.EventsOrganizer.repo.ClubRepo;
import com.example.EventsOrganizer.repo.EventRepo;
import com.example.EventsOrganizer.repo.UserRepo;
import com.example.EventsOrganizer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepo userRepo;
    private final ClubRepo clubRepo;
    private final EventRepo eventRepo;
    private final PasswordEncoder passwordEncoder;



    @Override
    public UserDto findById(long userId) {
        User user = userRepo.findUserById(userId);
        return mapToUserDto(user);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepo.findAll();
        return users.stream().map((user) -> mapToUserDto(user)).collect(Collectors.toList());
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        User user = mapToUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(List.of(Role.USER));
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

    @Override
    public UserDto updateUser(UserDto userDto, long userId) {
        User user = userRepo.findUserById(userId);

         String login = userDto.getLogin();
         String password = userDto.getPassword();
         String name = userDto.getName();
         String surname = userDto.getSurname();
         String phoneNumber = userDto.getPhoneNumber();
         int age = userDto.getAge();

        if (login != null && !login.isBlank()) {
            user.setName(login);
        }
        if (password != null && !password.isBlank()) {
            String encodedPassword = passwordEncoder.encode(password);
            user.setPassword(encodedPassword);
        }
        if (name != null && !name.isBlank()) {
            user.setName(name);
        }
        if (surname != null && !surname.isBlank()) {
            user.setSurname(surname);
        }
        if (phoneNumber != null && !phoneNumber.isBlank()) {
            user.setPhoneNumber(phoneNumber);
        }
        if (age != 0) {
            user.setAge(age);
        }
        userRepo.save(user);
        return mapToUserDto(user);
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

    @Transactional
    @Override
    public UserDto unsubscribeFromClub(long userId, long clubId) {
        User user = userRepo.findUserById(userId);
        Club club = clubRepo.findClubById(clubId);

        user.getSubscribedClubs().remove(club);
        userRepo.save(user);

        return mapToUserDto(user);
    }

    @Transactional
    @Override
    public UserDto leaveTheEvent(long userId, long eventId) {
        User user = userRepo.findUserById(userId);
        Event event = eventRepo.findById(eventId);

        user.getJoinedEvents().remove(event);
        userRepo.save(user);

        return mapToUserDto(user);
    }

    @Override
    @Transactional
    public void deleteUser(long userId) {
        User user = userRepo.findUserById(userId);
        userRepo.delete(user);
    }


    private User mapToUser(UserDto userDto) {
        User user = User.builder()

                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .phoneNumber(userDto.getPhoneNumber())
                .age(userDto.getAge())
                .roles(userDto.getRoles())
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
                .roles(user.getRoles())
                .subscribedClubs(user.getSubscribedClubs())
                .ownClub(user.getOwnClub())
                .joinedEvents(user.getJoinedEvents())
                .build();

        return userDto;
    }

}
