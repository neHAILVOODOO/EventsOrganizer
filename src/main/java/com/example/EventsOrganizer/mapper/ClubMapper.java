package com.example.EventsOrganizer.mapper;

import com.example.EventsOrganizer.model.dto.club.GetClubForListDto;
import com.example.EventsOrganizer.model.entity.Club;
import org.springframework.stereotype.Component;

@Component
public class ClubMapper {

    public GetClubForListDto mapClubToGetClubForListDto(Club club) {

        return GetClubForListDto.builder()
                .id(club.getId())
                .name(club.getName())
                .thematics(club.getThematics())
                .build();

    }

}
