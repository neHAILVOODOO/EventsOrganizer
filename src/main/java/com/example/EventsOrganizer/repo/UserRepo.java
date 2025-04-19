package com.example.EventsOrganizer.repo;

import com.example.EventsOrganizer.model.entity.Club;
import com.example.EventsOrganizer.model.entity.Event;
import com.example.EventsOrganizer.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u JOIN u.subscribedClubs c WHERE c = :club")
    Page<User> findAllBySubscribedClub(@Param("club") Club club, Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.joinedEvents e WHERE e = :event")
    Page<User> findAllByJoinedEvent(@Param("event") Event event, Pageable pageable);

    Optional<User> findUserById(long userId);

    Optional<User> findUserByLogin(String login);

    Boolean existsUserByLogin(String login);

}
