package com.example.EventsOrganizer.service;

import com.example.EventsOrganizer.model.dto.EventDto;

import java.util.List;

public interface EventService {

    EventDto createEventForOwnClub(long userId,EventDto eventDto);

    List<EventDto> findEventsByClub(long clubId);

    EventDto findEventByClubAndEventId(long clubId, long eventId);

    EventDto updateEvent(long userId, EventDto eventDto, long eventId);

    void deleteEvent(long userId, long eventId);

    List<EventDto> findAllByUser(long userId);

}
