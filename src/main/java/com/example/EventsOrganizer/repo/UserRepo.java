package com.example.EventsOrganizer.repo;

import com.example.EventsOrganizer.model.dto.UserDto;
import com.example.EventsOrganizer.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u JOIN u.subscribedClubs c ON c.id = :clubId")
    List<User> findAllBySubscribedClubId(@Param("clubId") Long clubId);

}
