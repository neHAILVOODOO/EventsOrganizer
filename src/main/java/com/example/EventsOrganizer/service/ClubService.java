package com.example.EventsOrganizer.service;

import com.example.EventsOrganizer.model.dto.ClubDto;
import com.example.EventsOrganizer.model.dto.club.GetClubForListDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClubService {

    ClubDto saveClub(long userId,ClubDto clubDto);

    List<ClubDto> getAllClubs();

    ClubDto findClubById(long clubId);

    ClubDto updateOwnerClub(long userId,ClubDto clubDto);

    ClubDto updateClub(long clubId, ClubDto clubDto);

    void deleteOwnClub(long userId);

    void adminDeleteClub(long clubId);

    Page<GetClubForListDto> findAllByUser(long userId, int page, int size, String sortBy, String direction);

}
