package com.example.EventsOrganizer.repo;

import com.example.EventsOrganizer.model.entity.Club;
import com.example.EventsOrganizer.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClubRepo extends JpaRepository<Club,Long> {

    Club findClubById(long clubId);

    @Query("SELECT DISTINCT c FROM Club c JOIN c.users u WHERE u = :user")
    Page<Club> findAllByUser(@Param("user") User user, Pageable pageable);

}
