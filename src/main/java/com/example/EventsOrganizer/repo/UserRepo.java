package com.example.EventsOrganizer.repo;

import com.example.EventsOrganizer.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

}
