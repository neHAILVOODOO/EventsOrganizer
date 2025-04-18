package com.example.EventsOrganizer.service.impl;

import com.example.EventsOrganizer.exception.NotFoundException;
import com.example.EventsOrganizer.mapper.ClubMapper;
import com.example.EventsOrganizer.model.dto.club.CreateClubDto;
import com.example.EventsOrganizer.model.dto.club.GetClubDto;
import com.example.EventsOrganizer.model.dto.club.GetClubForListDto;
import com.example.EventsOrganizer.model.dto.club.UpdateClubDto;
import com.example.EventsOrganizer.model.entity.Club;
import com.example.EventsOrganizer.model.entity.Event;
import com.example.EventsOrganizer.model.entity.User;
import com.example.EventsOrganizer.repo.ClubRepo;
import com.example.EventsOrganizer.repo.UserRepo;
import com.example.EventsOrganizer.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.core.internal.Function;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;



@Service
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService {

    private final ClubRepo clubRepo;
    private final UserRepo userRepo;

    private final ClubMapper clubMapper;

    @Override
    @Transactional
    public void createClub(long userId, CreateClubDto createClubDto) {
      User user = userRepo.findUserById(userId)
              .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        clubRepo.findClubByOwner(user)
                .ifPresent(club -> {
                    throw new IllegalStateException("У пользователя уже есть свой клуб.");
                });

          Club club = clubMapper.mapCreateClubDtoToClub(createClubDto);
          club.setOwner(user);
          clubRepo.save(club);
    }

    @Override
    public Page<GetClubForListDto> getAllClubs(int page, int size, String sortBy, String direction) {
        return findClubsByFunction(page, size, sortBy, direction, clubRepo::findAll);
    }

    @Override
    @Transactional
    public GetClubDto findClubById(long clubId) {
        Club club = clubRepo.findClubById(clubId)
                .orElseThrow(() -> new NotFoundException("Клуб не найден"));

        return clubMapper.mapClubToGetClubDto(club);
    }

    @Override
    @Transactional
    public void updateClub(long clubId, UpdateClubDto updateClubDto) {

        Club club = clubRepo.findClubById(clubId)
                .orElseThrow(() -> new NotFoundException("Клуб не найден"));

        BeanUtils.copyProperties(updateClubDto, club);
        clubRepo.save(club);
    }


    @Override
    @Transactional
    public void deleteClub(long clubId) {
        Club club = clubRepo.findClubById(clubId)
                .orElseThrow(() -> new NotFoundException("Клуб не найден"));

            clubRepo.delete(club);
    }


    @Override
    @Transactional
    public Page<GetClubForListDto> findAllSubscribedByUser(long userId, int page, int size, String sortBy, String direction) {
        User user = userRepo.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Function<Pageable, Page<Club>> clubFinder = (pageable) ->
                clubRepo.findAllSubscribedByUser(user, pageable);

        return findClubsByFunction(page, size, sortBy, direction, clubFinder);
    }


    private Sort createSort(String sortBy, String direction) {
        Set<String> allowedFields = Set.of("id", "name");
        String validSortBy = allowedFields.contains(sortBy) ? sortBy : "id";

        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        return Sort.by(sortDirection, validSortBy);
    }


    private Page<GetClubForListDto> findClubsByFunction(
            int page,
            int size,
            String sortBy,
            String direction,
            Function<Pageable, Page<Club>> clubFinder
    ) {
        int validSize = List.of(5, 10, 15).contains(size) ? size : 10;
        Sort sort = createSort(sortBy, direction);
        Pageable pageable = PageRequest.of(page, validSize, sort);

        return clubFinder.apply(pageable)
                .map(clubMapper::mapClubToGetClubForListDto);
    }

}
