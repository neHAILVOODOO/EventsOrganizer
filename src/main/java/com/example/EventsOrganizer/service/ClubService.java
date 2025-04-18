package com.example.EventsOrganizer.service;

import com.example.EventsOrganizer.model.dto.club.CreateClubDto;
import com.example.EventsOrganizer.model.dto.club.GetClubDto;
import com.example.EventsOrganizer.model.dto.club.GetClubForListDto;
import com.example.EventsOrganizer.model.dto.club.UpdateClubDto;
import org.springframework.data.domain.Page;

public interface ClubService {

    void createClub(long userId, CreateClubDto createClubDto);

    Page<GetClubForListDto> getAllClubs(int page, int size, String sortBy, String direction);

    GetClubDto findClubById(long clubId);

    void updateClub(long clubId, UpdateClubDto updateClubDto);

    void deleteClub(long clubId);

    Page<GetClubForListDto> findAllSubscribedByUser(long userId, int page, int size, String sortBy, String direction);

}
