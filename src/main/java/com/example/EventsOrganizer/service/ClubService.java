package com.example.EventsOrganizer.service;

import com.example.EventsOrganizer.model.dto.ClubDto;

import java.util.List;

public interface ClubService {

    ClubDto saveClub(ClubDto clubDto);

    List<ClubDto> getAllClubs();

    ClubDto findClubById(long clubId);

}
