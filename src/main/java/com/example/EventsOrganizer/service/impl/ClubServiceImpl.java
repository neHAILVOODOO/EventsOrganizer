package com.example.EventsOrganizer.service.impl;

import com.example.EventsOrganizer.model.dto.ClubDto;
import com.example.EventsOrganizer.model.dto.UserDto;
import com.example.EventsOrganizer.model.entity.Club;
import com.example.EventsOrganizer.model.entity.User;
import com.example.EventsOrganizer.repo.ClubRepo;
import com.example.EventsOrganizer.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ClubServiceImpl implements ClubService {


    @Autowired
    private ClubRepo clubRepo;

    public ClubServiceImpl(ClubRepo clubRepo) {
        this.clubRepo = clubRepo;
    }

    @Override
    public ClubDto saveClub(ClubDto clubDto) {
       Club club = mapToClub(clubDto);
       clubRepo.save(club);
       return mapToClubDto(club);
    }

    @Override
    public List<ClubDto> getAllClubs() {
        List<Club> clubs = clubRepo.findAll();
        return clubs.stream().map((club) -> mapToClubDto(club)).collect(Collectors.toList());
    }

    @Override
    public ClubDto findClubById(long clubId) {
        Club club = clubRepo.findClubById(clubId);
        return mapToClubDto(club);
    }


    private Club mapToClub(ClubDto clubDto) {
        Club club = Club.builder()

                .name(clubDto.getName())
                .description(clubDto.getDescription())
                .thematics(clubDto.getThematics())
                .users(clubDto.getUsers())
                .owner(clubDto.getOwner())
                .events(clubDto.getEvents())
                .build();

        return club;
    }

    private ClubDto mapToClubDto(Club club) {
        ClubDto clubDto = ClubDto.builder()

                .id(club.getId())
                .name(club.getName())
                .description(club.getDescription())
                .thematics(club.getThematics())
                .users(club.getUsers())
                .owner(club.getOwner())
                .events(club.getEvents())
                .build();

        return clubDto;
    }

}
