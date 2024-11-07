package com.example.EventsOrganizer.service.impl;

import com.example.EventsOrganizer.model.dto.ClubDto;
import com.example.EventsOrganizer.model.dto.UserDto;
import com.example.EventsOrganizer.model.entity.Club;
import com.example.EventsOrganizer.model.entity.Event;
import com.example.EventsOrganizer.model.entity.User;
import com.example.EventsOrganizer.repo.ClubRepo;
import com.example.EventsOrganizer.repo.EventRepo;
import com.example.EventsOrganizer.repo.UserRepo;
import com.example.EventsOrganizer.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ClubServiceImpl implements ClubService {


    @Autowired
    private ClubRepo clubRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private EventRepo eventRepo;

    public ClubServiceImpl(ClubRepo clubRepo, UserRepo userRepo, EventRepo eventRepo) {
        this.clubRepo = clubRepo;
        this.userRepo = userRepo;
        this.eventRepo = eventRepo;
    }

    @Override
    public ClubDto saveClub(ClubDto clubDto) {
       Club club = mapToClub(clubDto);
       clubRepo.save(club);
       return mapToClubDto(club);
    }

    @Override
    public List<ClubDto> getAllClubs() {
        List<Club> clubs = clubRepo.findAll();
        return clubs.stream().map((club) -> mapToClubDto(club)).collect(Collectors.toList());
    }

    @Override
    public ClubDto findClubById(long clubId) {
        Club club = clubRepo.findClubById(clubId);
        return mapToClubDto(club);
    }

    @Override
    public ClubDto updateClub(ClubDto clubDto, long clubId) {
        Club club = clubRepo.findClubById(clubId);

        String name = clubDto.getName();
        String description = clubDto.getDescription();
        String thematics = clubDto.getThematics();

        if (name != null && !name.isBlank()) {
            club.setName(name);
        }
        if (description != null && !description.isBlank()) {
            club.setDescription(description);
        }
        if (thematics != null && !thematics.isBlank()) {
            club.setThematics(thematics);
        }

        clubRepo.save(club);
        return mapToClubDto(club);
    }

    @Override
    @Transactional
    public void deleteClub(long clubId) {
        Club club = clubRepo.findClubById(clubId);
        clubRepo.delete(club);

        User owner = userRepo.findUserById(club.getOwner().getId());
        owner.setOwnClub(null);
        userRepo.save(owner);

        List<Event> events = club.getEvents();
        events.forEach(event -> {

            List<User> joinedUsers = event.getJoinedUsers();
            joinedUsers.forEach(user -> {
                user.getJoinedEvents().remove(event);
                userRepo.save(user);
            });


            eventRepo.delete(event);
        });

        List<User> subscribers = club.getUsers();

        subscribers.forEach(user -> {
            user.getSubscribedClubs().remove(club);
            userRepo.save(user);
        });

    }

    @Override
    public List<ClubDto> findAllByUser(long userId) {
        List<Club> clubs = clubRepo.findAllByUser(userId);
        return clubs.stream().map((club) -> mapToClubDto(club)).collect(Collectors.toList());
    }


    private Club mapToClub(ClubDto clubDto) {
        Club club = Club.builder()

                .name(clubDto.getName())
                .description(clubDto.getDescription())
                .thematics(clubDto.getThematics())
                .users(clubDto.getUsers())
                .owner(clubDto.getOwner())
                .events(clubDto.getEvents())
                .build();

        return club;
    }

    private ClubDto mapToClubDto(Club club) {
        ClubDto clubDto = ClubDto.builder()

                .id(club.getId())
                .name(club.getName())
                .description(club.getDescription())
                .thematics(club.getThematics())
                .users(club.getUsers())
                .owner(club.getOwner())
                .events(club.getEvents())
                .build();

        return clubDto;
    }

}
