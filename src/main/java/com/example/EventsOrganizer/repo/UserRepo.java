package com.example.EventsOrganizer.repo;


import com.example.EventsOrganizer.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u JOIN u.subscribedClubs c ON c.id = :clubId")
    List<User> findAllBySubscribedClubId(@Param("clubId") long clubId);

    @Query("SELECT u FROM User u JOIN u.joinedEvents c ON c.id = :eventId")
    List<User> findAllByJoinedEventId(@Param("eventId") long eventId);

    User findUserById(long userId);

    User findUserByLogin(String login);

    Boolean existsUserByLogin(String login);

}
