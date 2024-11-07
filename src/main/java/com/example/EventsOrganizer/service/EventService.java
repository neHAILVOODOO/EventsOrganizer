package com.example.EventsOrganizer.service;

import com.example.EventsOrganizer.model.dto.EventDto;

import java.util.List;

public interface EventService {

    EventDto createEventForClub(EventDto eventDto, long clubId);

    List<EventDto> findEventsByClub(long clubId);

    EventDto findEventByClubAndEventId(long clubId, long eventId);

    EventDto updateEvent(EventDto eventDto, long clubId, long eventId);

    void deleteEvent(long clubId, long eventId);

    List<EventDto> findAllByUser(long userId);

}
