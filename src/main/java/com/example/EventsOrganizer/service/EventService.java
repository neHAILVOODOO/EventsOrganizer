package com.example.EventsOrganizer.service;

import com.example.EventsOrganizer.model.dto.EventDto;
import com.example.EventsOrganizer.model.dto.event.GetEventForListDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EventService {

    EventDto createEventForOwnClub(long userId,EventDto eventDto);

    List<EventDto> findEventsByClub(long clubId);

    EventDto findEventByClubAndEventId(long clubId, long eventId);

    EventDto updateEvent(long userId, EventDto eventDto, long eventId);

    void deleteEvent(long userId, long eventId);

    Page<GetEventForListDto> findAllByUser(long userId, int page, int size, String sortBy, String direction);

}
