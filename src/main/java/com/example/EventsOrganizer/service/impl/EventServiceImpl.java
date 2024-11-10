package com.example.EventsOrganizer.service.impl;

import com.example.EventsOrganizer.model.dto.EventDto;
import com.example.EventsOrganizer.model.entity.Club;
import com.example.EventsOrganizer.model.entity.Event;
import com.example.EventsOrganizer.model.entity.User;
import com.example.EventsOrganizer.repo.ClubRepo;
import com.example.EventsOrganizer.repo.EventRepo;
import com.example.EventsOrganizer.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class EventServiceImpl implements com.example.EventsOrganizer.service.EventService {

    @Autowired
    private final EventRepo eventRepo;
    @Autowired
    private final ClubRepo clubRepo;
    @Autowired UserRepo userRepo;

    public EventServiceImpl(EventRepo eventRepo, ClubRepo clubRepo, UserRepo userRepo) {
        this.eventRepo = eventRepo;
        this.clubRepo = clubRepo;
        this.userRepo = userRepo;
    }

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
    public List<EventDto> findEventsByClub(long clubId) {
        Club club = clubRepo.findClubById(clubId);
        List<Event> events =  eventRepo.findAllByOrganizingClub(club);
        return events.stream().map((event) -> mapToEventDto(event)).collect(Collectors.toList());
    }

    @Override
    public EventDto findEventByClubAndEventId(long clubId, long eventId) {
        Event event = eventRepo.findByOrganizingClub_IdAndId(clubId,eventId);
        return mapToEventDto(event);
    }

    @Transactional
    @Override
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
    public List<EventDto> findAllByUser(long userId) {
        List<Event> events = eventRepo.findAllByUser(userId);
        return events.stream().map((event) -> mapToEventDto(event)).collect(Collectors.toList());
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

}
