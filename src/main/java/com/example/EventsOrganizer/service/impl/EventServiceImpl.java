package com.example.EventsOrganizer.service.impl;

import com.example.EventsOrganizer.model.dto.EventDto;
import com.example.EventsOrganizer.model.entity.Club;
import com.example.EventsOrganizer.model.entity.Event;
import com.example.EventsOrganizer.repo.ClubRepo;
import com.example.EventsOrganizer.repo.EventRepo;
import com.example.EventsOrganizer.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class EventServiceImpl implements com.example.EventsOrganizer.service.EventService {

    @Autowired
    private final EventRepo eventRepo;
    @Autowired
    private final ClubRepo clubRepo;

    public EventServiceImpl(EventRepo eventRepo, ClubRepo clubRepo) {
        this.eventRepo = eventRepo;
        this.clubRepo = clubRepo;
    }

    @Override
    public EventDto createEventForClub(EventDto eventDto,long clubId) {
        Club club = clubRepo.findClubById(clubId);
        eventDto.setOrganizingClub(club);

        Event event = mapToEvent(eventDto);
        eventRepo.save(event);

        return eventDto;
    }

    @Override
    public List<EventDto> getEventsFromClub(long clubId) {
        Club club = clubRepo.findClubById(clubId);
        List<Event> events =  eventRepo.findAllByOrganizingClub(club);
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
