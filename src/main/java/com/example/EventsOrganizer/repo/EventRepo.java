package com.example.EventsOrganizer.repo;

import com.example.EventsOrganizer.model.entity.Club;
import com.example.EventsOrganizer.model.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepo extends JpaRepository<Event, Long> {

    List<Event> findAllByOrganizingClub(Club club);

    Event findByOrganizingClub_IdAndId(long clubId, long eventId);

    Event findById(long eventId);

    @Query("SELECT e FROM Event e JOIN e.joinedUsers u ON u.id = :userId")
    List<Event> findAllByUser(long userId);

}
