package com.example.EventsOrganizer.service.impl;

import com.example.EventsOrganizer.exception.NotFoundException;
import com.example.EventsOrganizer.mapper.UserMapper;
import com.example.EventsOrganizer.model.dto.user.CreateUserDto;
import com.example.EventsOrganizer.model.dto.user.GetUserDto;
import com.example.EventsOrganizer.model.dto.user.GetUserForListDto;
import com.example.EventsOrganizer.model.entity.Club;
import com.example.EventsOrganizer.model.entity.Event;
import com.example.EventsOrganizer.model.entity.User;
import com.example.EventsOrganizer.repo.ClubRepo;
import com.example.EventsOrganizer.repo.EventRepo;
import com.example.EventsOrganizer.repo.UserRepo;
import com.example.EventsOrganizer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.internal.Function;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final ClubRepo clubRepo;
    private final EventRepo eventRepo;

    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public GetUserDto findById(long userId) {
        User user = userRepo.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));;
        return userMapper.mapUserToGetUserDto(user);
    }

    @Override
    @Transactional
    public Page<GetUserForListDto> findAllUsers(int page, int size, String sortBy, String direction) {
        return findUsers(page, size, sortBy, direction, userRepo::findAll);
    }

    @Override
    @Transactional
    public void saveUser(CreateUserDto createUserDto) {

        if (existsByUser(createUserDto.getLogin())) {
            throw new IllegalStateException("Пользователь с таким логином уже существует");
        }
            User user = userMapper.mapCreateUserDtoToUser(createUserDto);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);

    }

    @Override
    @Transactional
    public List<UserDto> findAllBySubscribedClubId(long clubId) {
        List<User> users = userRepo.findAllBySubscribedClubId(clubId);
        return users.stream().map((user) -> mapToUserDto(user)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<UserDto> findAllByJoinedEventId(long eventId) {
        List<User> users = userRepo.findAllByJoinedEventId(eventId);
        return users.stream().map((user) -> mapToUserDto(user)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDto updateUser(UserDto userDto, long userId) {
        User user = userRepo.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));;

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


    @Override
    @Transactional
    public UserDto subscribeToClub(long userId, long clubId) {
        User user = userRepo.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Club club = clubRepo.findClubById(clubId);

        if (club.getUsers().contains(user)) {

            throw new IllegalStateException("Пользователь уже подписан на этот клуб.");

        } else  {
            user.getSubscribedClubs().add(club);
            userRepo.save(user);
            return mapToUserDto(user);
        }
    }


    @Override
    @Transactional
    public UserDto jointToTheEvent(long userId, long eventId, long clubId) {
       User user = userRepo.findUserById(userId)
               .orElseThrow(() -> new NotFoundException("Пользователь не найден"));;

       Event event = eventRepo.findByOrganizingClub_IdAndId(clubId, eventId);

       if (event.getJoinedUsers().contains(user)) {

           throw new IllegalStateException("Пользователь уже присоединился к этому событию.");

       } else {
           user.getJoinedEvents().add(event);
           userRepo.save(user);
           return mapToUserDto(user);
       }
    }


    @Override
    @Transactional
    public UserDto unsubscribeFromClub(long userId, long clubId) {
        User user = userRepo.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Club club = clubRepo.findClubById(clubId);

        user.getSubscribedClubs().remove(club);
        userRepo.save(user);

        return mapToUserDto(user);
    }


    @Override
    @Transactional
    public UserDto leaveTheEvent(long userId, long eventId) {
        User user = userRepo.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие не найдено"));

        user.getJoinedEvents().remove(event);
        userRepo.save(user);

        return mapToUserDto(user);
    }

    @Override
    @Transactional
    public void deleteUser(long userId) {
        User user = userRepo.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        userRepo.delete(user);
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

    private Sort createSort(String sortBy, String direction) {
        Set<String> allowedFields = Set.of("id", "name");
        String validSortBy = allowedFields.contains(sortBy) ? sortBy : "id";

        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        return Sort.by(sortDirection, validSortBy);
    }

    private Page<GetUserForListDto> findUsers(
            int page,
            int size,
            String sortBy,
            String direction,
            Function<Pageable, Page<User>> userFinder
    ) {
        int validSize = List.of(5, 10, 15).contains(size) ? size : 10;
        Sort sort = createSort(sortBy, direction);
        Pageable pageable = PageRequest.of(page, validSize, sort);

        return userFinder.apply(pageable)
                .map(userMapper::mapUserToGetUserForListDto);
    }


    public Boolean existsByUser(String login) {
        return userRepo.existsUserByLogin(login);
    }

}
