package com.example.EventsOrganizer.repo;

import com.example.EventsOrganizer.model.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepo extends JpaRepository<Club,Long> {

Club findClubById(long clubId);


}
