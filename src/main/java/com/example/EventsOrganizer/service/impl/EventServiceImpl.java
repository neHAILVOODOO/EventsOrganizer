package com.example.EventsOrganizer.service.impl;

import com.example.EventsOrganizer.mapper.EventMapper;
import com.example.EventsOrganizer.model.dto.EventDto;
import com.example.EventsOrganizer.model.dto.event.GetEventForListDto;
import com.example.EventsOrganizer.model.entity.Club;
import com.example.EventsOrganizer.model.entity.Event;
import com.example.EventsOrganizer.model.entity.User;
import com.example.EventsOrganizer.repo.ClubRepo;
import com.example.EventsOrganizer.repo.EventRepo;
import com.example.EventsOrganizer.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EventServiceImpl implements com.example.EventsOrganizer.service.EventService {

    private final EventRepo eventRepo;
    private final ClubRepo clubRepo;
    private final UserRepo userRepo;

    private final EventMapper eventMapper;

    @Override
    @Transactional
    public EventDto createEventForOwnClub(long userId,EventDto eventDto) {
        User owner = userRepo.findUserById(userId);

        if (owner.getOwnClub() != null) {

            Club club = owner.getOwnClub();
            eventDto.setOrganizingClub(club);

            Event event = mapToEvent(eventDto);
            eventRepo.save(event);

            return eventDto;
        } else {
            throw new IllegalStateException("У пользователя нет своего клуба.");
        }
    }

    @Override
    @Transactional
    public List<EventDto> findEventsByClub(long clubId) {
        Club club = clubRepo.findClubById(clubId);
        List<Event> events =  eventRepo.findAllByOrganizingClub(club);
        return events.stream().map((event) -> mapToEventDto(event)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventDto findEventByClubAndEventId(long clubId, long eventId) {
        Event event = eventRepo.findByOrganizingClub_IdAndId(clubId,eventId);
        return mapToEventDto(event);
    }


    @Override
    @Transactional
    public EventDto updateEvent(long userId, EventDto eventDto, long eventId) {
       User owner = userRepo.findUserById(userId);

       if (owner.getOwnClub() != null) {

           Club ownClub = owner.getOwnClub();
           Event event = eventRepo.findByOrganizingClub_IdAndId(ownClub.getId(), eventId);

           if (event != null) {

               String name = eventDto.getName();
               String description = eventDto.getDescription();
               Date beginDate = eventDto.getBeginDate();
               Date endDate = eventDto.getEndDate();
               String status = eventDto.getStatus();

               if (name != null && !name.isBlank()) {
                   event.setName(name);
               }
               if (description != null && !description.isBlank()) {
                   event.setDescription(description);
               }
               if (beginDate != null) {
                   event.setBeginDate(beginDate);
               }

               if (endDate != null) {
                   event.setEndDate(endDate);
               }

               if (status != null && !status.isBlank()) {
                   event.setStatus(status);
               }

               eventRepo.save(event);
               return mapToEventDto(event);
           } else {
               throw new IllegalStateException("У клуба нет такого ивента.");
           }

       } else {
           throw new IllegalStateException("У пользователя нет своего клуба.");
       }
    }

    @Override
    @Transactional
    public void deleteEvent(long userId, long eventId) {

        User owner = userRepo.findUserById(userId);

        if (owner.getOwnClub() != null) {

            Club ownClub = owner.getOwnClub();
            Event event = eventRepo.findByOrganizingClub_IdAndId(ownClub.getId(), eventId);

            if (event != null) {

                eventRepo.delete(event);

                Club organizingClub = event.getOrganizingClub();
                organizingClub.getEvents().remove(event);
                clubRepo.save(organizingClub);

                List<User> joinedUsers = event.getJoinedUsers();

                if (!joinedUsers.isEmpty()) {
                    joinedUsers.forEach(user -> {
                        user.getJoinedEvents().remove(event);
                        userRepo.save(user);
                    });
                }
            } else {
                throw new IllegalStateException("У клуба нет такого ивента.");
            }
        } else {
            throw new IllegalStateException("У пользователя нет своего клуба.");
        }
    }

    @Override
    @Transactional
    public Page<GetEventForListDto> findAllByUser(long userId, int page, int size, String sortBy, String direction) {
        return findEventsByUser(userId, page, size, sortBy, direction, eventRepo::findAllByUser);
    }

    private Event mapToEvent(EventDto eventDto) {

    Event event = Event.builder()
            .name(eventDto.getName())
            .description(eventDto.getDescription())
            .beginDate(eventDto.getBeginDate())
            .endDate(eventDto.getEndDate())
            .status(eventDto.getStatus())
            .joinedUsers(eventDto.getJoinedUsers())
            .organizingClub(eventDto.getOrganizingClub())
            .build();

    return event;
    }

    private EventDto mapToEventDto(Event event) {

        EventDto eventDto = EventDto.builder()
                .id(event.getId())
                .name(event.getName())
                .description(event.getDescription())
                .beginDate(event.getBeginDate())
                .endDate(event.getEndDate())
                .status(event.getStatus())
                .joinedUsers(event.getJoinedUsers())
                .organizingClub(event.getOrganizingClub())
                .build();

        return eventDto;
    }

    private Sort createSort(String sortBy, String direction) {
        Set<String> allowedFields = Set.of("id", "name");
        String validSortBy = allowedFields.contains(sortBy) ? sortBy : "id";

        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        return Sort.by(sortDirection, validSortBy);
    }

    private Page<GetEventForListDto> findEventsByUser(
            long userId,
            int page,
            int size,
            String sortBy,
            String direction,
            BiFunction<User, Pageable, Page<Event>> eventFinder
    ) {
        int validSize = List.of(5, 10, 15).contains(size) ? size : 10;
        Sort sort = createSort(sortBy, direction);
        Pageable pageable = PageRequest.of(page, validSize, sort);

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NullPointerException("Пользователь не найден"));

        return eventFinder.apply(user, pageable)
                .map(eventMapper::mapEventToGetEventDtoForList);
    }


}
