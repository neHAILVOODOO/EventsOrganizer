package com.example.EventsOrganizer.service.impl;

import com.example.EventsOrganizer.exception.ConflictException;
import com.example.EventsOrganizer.exception.NotFoundException;
import com.example.EventsOrganizer.mapper.UserMapper;
import com.example.EventsOrganizer.model.dto.user.CreateUserDto;
import com.example.EventsOrganizer.model.dto.user.GetUserDto;
import com.example.EventsOrganizer.model.dto.user.GetUserForListDto;
import com.example.EventsOrganizer.model.dto.user.UpdateUserBioDto;
import com.example.EventsOrganizer.model.dto.user.UpdateUserLoginDto;
import com.example.EventsOrganizer.model.dto.user.UpdateUserPasswordDto;
import com.example.EventsOrganizer.model.entity.Club;
import com.example.EventsOrganizer.model.entity.Event;
import com.example.EventsOrganizer.model.entity.User;
import com.example.EventsOrganizer.repo.ClubRepo;
import com.example.EventsOrganizer.repo.EventRepo;
import com.example.EventsOrganizer.repo.UserRepo;
import com.example.EventsOrganizer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        return userMapper.mapUserToGetUserDto(user);
    }

    @Override
    @Transactional
    public Page<GetUserForListDto> findAllUsers(int page, int size, String sortBy, String direction) {
        return findUsersByFunction(page, size, sortBy, direction, userRepo::findAll);
    }

    @Override
    @Transactional
    public void saveUser(CreateUserDto createUserDto) {
        if (userRepo.existsUserByLogin(createUserDto.getLogin())) {
            throw new ConflictException("Пользователь с таким логином уже существует");
        }

        User user = userMapper.mapCreateUserDtoToUser(createUserDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    @Override
    @Transactional
    public Page<GetUserForListDto> findAllBySubscribedClubId(long clubId, int page, int size, String sortBy, String direction) {
        Club club = clubRepo.findClubById(clubId)
                .orElseThrow(() -> new NotFoundException("Клуб не найден"));

        Function<Pageable, Page<User>> userFinder = (pageable) ->
                userRepo.findAllBySubscribedClub(club, pageable);

       return findUsersByFunction(page, size, sortBy, direction, userFinder);
    }

    @Override
    @Transactional
    public Page<GetUserForListDto> findAllByJoinedEventId(long eventId, int page, int size, String sortBy, String direction) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие не найдено"));

        Function<Pageable, Page<User>> userFinder = (pageable) ->
                userRepo.findAllByJoinedEvent(event, pageable);

        return findUsersByFunction(page, size, sortBy, direction, userFinder);
    }

    @Override
    @Transactional
    public void updateUserBio(UpdateUserBioDto updateUserBioDto, long userId) {
        User user = userRepo.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        BeanUtils.copyProperties(updateUserBioDto, user);
        userRepo.save(user);
    }

    @Override
    public void updateUserLogin(UpdateUserLoginDto updateUserLoginDto, long userId) {
        User user = userRepo.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        if (!passwordEncoder.matches(updateUserLoginDto.getPassword(), user.getPassword())) {
            throw new ConflictException("Неверный текущий пароль");
        }

        if (userRepo.existsUserByLogin(updateUserLoginDto.getNewLogin())) {
            throw new ConflictException("Логин уже занят");
        }

        user.setLogin(updateUserLoginDto.getNewLogin());
        userRepo.save(user);
    }

    @Override
    public void updateUserPassword(UpdateUserPasswordDto updateUserPasswordDto, long userId) {
        User user = userRepo.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        if (!passwordEncoder.matches(updateUserPasswordDto.getCurrentPassword(), user.getPassword())) {
            throw new ConflictException("Неверный текущий пароль");
        }

        if (passwordEncoder.matches(updateUserPasswordDto.getNewPassword(), user.getPassword())) {
            throw new ConflictException("Новый пароль должен отличаться от текущего");
        }

        if (!updateUserPasswordDto.getNewPassword().equals(updateUserPasswordDto.getConfirmNewPassword())) {
            throw new ConflictException("Пароли должны совпадать");
        }

        user.setPassword(passwordEncoder.encode(updateUserPasswordDto.getNewPassword()));
        userRepo.save(user);
    }


    @Override
    @Transactional
    public void subscribeToClub(long userId, long clubId) {
        User user = userRepo.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Club club = clubRepo.findClubById(clubId)
                .orElseThrow(() -> new NotFoundException("Клуб не найден"));

        if (club.getUsers().contains(user)) {
            throw new ConflictException("Пользователь уже подписан на этот клуб.");
        }
        user.getSubscribedClubs().add(club);
        userRepo.save(user);
    }


    @Override
    @Transactional
    public void jointToTheEvent(long userId, long eventId) {
       User user = userRepo.findUserById(userId)
               .orElseThrow(() -> new NotFoundException("Пользователь не найден"));;

        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие не найдено"));

       if (event.getJoinedUsers().contains(user)) {
           throw new IllegalStateException("Пользователь уже присоединился к этому событию.");
       }

           user.getJoinedEvents().add(event);
           userRepo.save(user);
    }


    @Override
    @Transactional
    public void unsubscribeFromClub(long userId, long clubId) {
        User user = userRepo.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Club club = clubRepo.findClubById(clubId)
                .orElseThrow(() -> new NotFoundException("Клуб не найден"));

        user.getSubscribedClubs().remove(club);
        userRepo.save(user);
    }


    @Override
    @Transactional
    public void leaveTheEvent(long userId, long eventId) {
        User user = userRepo.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));;

        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие не найдено"));

        user.getJoinedEvents().remove(event);
        userRepo.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(long userId) {
        User user = userRepo.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        userRepo.delete(user);
    }


    private Sort createSort(String sortBy, String direction) {
        Set<String> allowedFields = Set.of("id", "name");
        String validSortBy = allowedFields.contains(sortBy) ? sortBy : "id";

        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        return Sort.by(sortDirection, validSortBy);
    }

    private Page<GetUserForListDto> findUsersByFunction(
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


}
