package com.example.EventsOrganizer.service;

import com.example.EventsOrganizer.model.dto.EventDto;

import java.util.List;

public interface EventService {

    EventDto createEventForClub(EventDto eventDto, long clubId);

    List<EventDto> getEventsFromClub(long clubId);

}
