package com.example.EventsOrganizer.service;

import com.example.EventsOrganizer.model.dto.event.CreateEventDto;
import com.example.EventsOrganizer.model.dto.event.GetEventDto;
import com.example.EventsOrganizer.model.dto.event.GetEventForListDto;
import com.example.EventsOrganizer.model.dto.event.UpdateEventInfoDto;
import org.springframework.data.domain.Page;

public interface EventService {

    void createEventForOwnClub(long userId, CreateEventDto createEventDto);

    Page<GetEventForListDto> findEventsByClub(long clubId, int page, int size, String sortBy, String direction);

    GetEventDto findEventById(long eventId);

    void updateEventInfo(UpdateEventInfoDto updateEventInfoDto, long eventId);

    void deleteEvent(long eventId);

    Page<GetEventForListDto> findAllJoinedByUser(long userId, int page, int size, String sortBy, String direction);

}
