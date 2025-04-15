package com.example.EventsOrganizer.service;

import com.example.EventsOrganizer.model.dto.EventDto;
import com.example.EventsOrganizer.model.dto.event.CreateEventDto;
import com.example.EventsOrganizer.model.dto.event.GetEventDto;
import com.example.EventsOrganizer.model.dto.event.GetEventForListDto;
import com.example.EventsOrganizer.model.dto.event.UpdateEventInfoDto;
import org.springframework.data.domain.Page;

public interface EventService {

    void createEventForOwnClub(long userId, CreateEventDto createEventDto);

    Page<GetEventForListDto> findEventsByClub(long clubId, int page, int size, String sortBy, String direction);

    GetEventDto findEventByClubAndEventId(long clubId, long eventId);

    void updateEventInfo(long userId, UpdateEventInfoDto updateEventInfoDto, long eventId);

    void deleteEvent(long userId, long eventId);

    Page<GetEventForListDto> findAllByUser(long userId, int page, int size, String sortBy, String direction);

}
