package com.example.EventsOrganizer.service.impl;

import com.example.EventsOrganizer.exception.NoSuchObjectException;
import com.example.EventsOrganizer.exception.NotFoundException;
import com.example.EventsOrganizer.mapper.EventMapper;
import com.example.EventsOrganizer.model.dto.event.CreateEventDto;
import com.example.EventsOrganizer.model.dto.event.GetEventDto;
import com.example.EventsOrganizer.model.dto.event.GetEventForListDto;
import com.example.EventsOrganizer.model.dto.event.UpdateEventInfoDto;
import com.example.EventsOrganizer.model.entity.Club;
import com.example.EventsOrganizer.model.entity.Event;
import com.example.EventsOrganizer.model.entity.User;
import com.example.EventsOrganizer.repo.ClubRepo;
import com.example.EventsOrganizer.repo.EventRepo;
import com.example.EventsOrganizer.repo.UserRepo;
import com.example.EventsOrganizer.service.EventService;
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
public class EventServiceImpl implements EventService {

    private final EventRepo eventRepo;
    private final ClubRepo clubRepo;
    private final UserRepo userRepo;

    private final EventMapper eventMapper;

    @Override
    @Transactional
    public void createEventForOwnClub(long userId, CreateEventDto createEventDto) {
        User owner = userRepo.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Club club = clubRepo.findClubByOwner(owner)
                .orElseThrow(() -> new NoSuchObjectException("У пользователя нет своего клуба"));

        Event event = eventMapper.mapCreateEventDtoToEvent(createEventDto);
        event.setOrganizingClub(club);

        eventRepo.save(event);
    }

    @Override
    @Transactional
    public Page<GetEventForListDto> findEventsByClub(long clubId, int page, int size, String sortBy, String direction) {
        Club club = clubRepo.findClubById(clubId)
                .orElseThrow(() -> new NotFoundException("Клуб не найден"));

        Function<Pageable, Page<Event>> eventFinder = (pageable) ->
                eventRepo.findAllByOrganizingClub(club, pageable);

        return findEventsByFunction(
                page,
                size,
                sortBy,
                direction,
                eventFinder
        );
    }

    @Override
    @Transactional
    public GetEventDto findEventById(long eventId) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие не найдено"));

        return eventMapper.mapEventToGetEventDto(event);
    }

    @Override
    @Transactional
    public void updateEventInfo(UpdateEventInfoDto updateEventInfoDto, long eventId) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие не найдено"));

        BeanUtils.copyProperties(updateEventInfoDto, event);
        eventRepo.save(event);
    }

    @Override
    @Transactional
    public void deleteEvent(long eventId) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие не найдено"));

        eventRepo.delete(event);
    }

    @Override
    @Transactional
    public Page<GetEventForListDto> findAllJoinedByUser(long userId, int page, int size, String sortBy, String direction) {
        User owner = userRepo.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Function<Pageable, Page<Event>> eventFinder = (pageable) ->
                eventRepo.findAllByUser(owner, pageable);

        return findEventsByFunction(page, size, sortBy, direction, eventFinder);
    }


    private Sort createSort(String sortBy, String direction) {
        Set<String> allowedFields = Set.of("id", "name");
        String validSortBy = allowedFields.contains(sortBy) ? sortBy : "id";

        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        return Sort.by(sortDirection, validSortBy);
    }

    private Page<GetEventForListDto> findEventsByFunction(
            int page,
            int size,
            String sortBy,
            String direction,
            Function<Pageable, Page<Event>> eventFinder
    ) {
        int validSize = List.of(5, 10, 15).contains(size) ? size : 10;
        Sort sort = createSort(sortBy, direction);
        Pageable pageable = PageRequest.of(page, validSize, sort);

        return eventFinder.apply(pageable)
                .map(eventMapper::mapEventToGetEventDtoForList);
    }

}
