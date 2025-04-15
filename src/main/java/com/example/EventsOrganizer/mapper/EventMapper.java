package com.example.EventsOrganizer.mapper;

import com.example.EventsOrganizer.model.dto.event.CreateEventDto;
import com.example.EventsOrganizer.model.dto.event.GetEventDto;
import com.example.EventsOrganizer.model.dto.event.GetEventForListDto;
import com.example.EventsOrganizer.model.entity.Event;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public GetEventForListDto mapEventToGetEventDtoForList(Event event) {
        return GetEventForListDto.builder()
                .id(event.getId())
                .name(event.getName())
                .organizingClubId(event.getOrganizingClub().getId())
                .organizingClubName(event.getOrganizingClub().getName())
                .build();
    }

    public Event mapCreateEventDtoToEvent(CreateEventDto createEventDto) {
        return Event.builder()
                .name(createEventDto.getName())
                .description(createEventDto.getDescription())
                .beginDate(createEventDto.getBeginDate())
                .endDate(createEventDto.getEndDate())
                .status("НОВЫЙ СТАТУС, ПОТОМ ПОМЕНЯЙ В МАППЕРЕ")
                .build();
    }

    public GetEventDto mapEventToGetEventDto(Event event) {
        return GetEventDto.builder()
                .id(event.getId())
                .name(event.getName())
                .description(event.getDescription())
                .beginDate(event.getBeginDate())
                .endDate(event.getEndDate())
                .status(event.getStatus())
                .build();
    }


}
