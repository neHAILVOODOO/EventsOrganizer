package com.example.EventsOrganizer.repo;

import com.example.EventsOrganizer.model.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClubRepo extends JpaRepository<Club,Long> {

    Club findClubById(long clubId);


    @Query("SELECT c FROM Club c JOIN c.users u ON u.id = :userId")
    List<Club> findAllByUser(long userId);

}
