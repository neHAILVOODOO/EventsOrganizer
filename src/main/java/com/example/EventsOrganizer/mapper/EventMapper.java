package com.example.EventsOrganizer.mapper;

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


}
