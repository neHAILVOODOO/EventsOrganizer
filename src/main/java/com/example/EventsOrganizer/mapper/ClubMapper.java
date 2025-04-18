package com.example.EventsOrganizer.mapper;

import com.example.EventsOrganizer.model.dto.club.CreateClubDto;
import com.example.EventsOrganizer.model.dto.club.GetClubDto;
import com.example.EventsOrganizer.model.dto.club.GetClubForListDto;
import com.example.EventsOrganizer.model.entity.Club;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClubMapper {

    private final UserMapper userMapper;

    public GetClubForListDto mapClubToGetClubForListDto(Club club) {

        return GetClubForListDto.builder()
                .id(club.getId())
                .name(club.getName())
                .thematics(club.getThematics())
                .build();

    }

    public Club mapCreateClubDtoToClub(CreateClubDto createClubDto) {

        return Club.builder()
                .name(createClubDto.getName())
                .description(createClubDto.getDescription())
                .thematics(createClubDto.getThematics())
                .build();
    }

    public GetClubDto mapClubToGetClubDto(Club club) {

        return GetClubDto.builder()
                .id(club.getId())
                .name(club.getName())
                .description(club.getDescription())
                .thematics(club.getThematics())
                .owner(userMapper.mapUserToUserPreviewDto(club.getOwner()))
                .build();

    }

}
