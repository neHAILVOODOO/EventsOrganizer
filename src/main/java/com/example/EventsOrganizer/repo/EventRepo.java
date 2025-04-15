package com.example.EventsOrganizer.repo;

import com.example.EventsOrganizer.model.entity.Club;
import com.example.EventsOrganizer.model.entity.Event;
import com.example.EventsOrganizer.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EventRepo extends JpaRepository<Event, Long> {

    Page<Event> findAllByOrganizingClub(Club club, Pageable pageable);

    Optional<Event> findByOrganizingClubAndId(Club club, long eventId);

    @Query("SELECT e FROM Event e JOIN e.joinedUsers u WHERE u = :user")
    Page<Event> findAllByUser(@Param("user") User user, Pageable pageable);

}
