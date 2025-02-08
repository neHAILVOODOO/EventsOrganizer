package com.example.EventsOrganizer.service.impl;

import com.example.EventsOrganizer.model.dto.ClubDto;
import com.example.EventsOrganizer.model.entity.Club;
import com.example.EventsOrganizer.model.entity.Event;
import com.example.EventsOrganizer.model.entity.User;
import com.example.EventsOrganizer.repo.ClubRepo;
import com.example.EventsOrganizer.repo.EventRepo;
import com.example.EventsOrganizer.repo.UserRepo;
import com.example.EventsOrganizer.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService {


    private final ClubRepo clubRepo;
    private final UserRepo userRepo;
    private final EventRepo eventRepo;


    @Transactional
    @Override
    public ClubDto saveClub(long userId, ClubDto clubDto) {
      User user = userRepo.findUserById(userId);

      if (user.getOwnClub() == null) {

          Club club = mapToClub(clubDto);
          club.setOwner(user);
          clubRepo.save(club);

          return mapToClubDto(club);

      } else {
          throw new IllegalStateException("У пользователя уже есть свой клуб.");
      }


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
    @Transactional
    public ClubDto updateOwnerClub(long userId, ClubDto clubDto) {

        User user = userRepo.findUserById(userId);

        if (user.getOwnClub() != null) {

            Club club = clubRepo.findClubById(user.getOwnClub().getId());

            changeClubToClubDtoData(club, clubDto);
            clubRepo.save(club);
            return mapToClubDto(club);

        } else {
            throw new IllegalStateException("У пользователя нет своего клуба.");
        }
    }


    @Override
    @Transactional
    public ClubDto updateClub(long clubId, ClubDto clubDto) {

        Club club = clubRepo.findClubById(clubId);

        if (club != null) {

            changeClubToClubDtoData(club, clubDto);
            clubRepo.save(club);
            return mapToClubDto(club);

        } else {
            throw new IllegalStateException("Клуба не существ");
        }
    }


    @Override
    @Transactional
    public void deleteOwnClub(long userId) {
        User owner = userRepo.findUserById(userId);

        if (owner.getOwnClub() != null) {

            Club club = owner.getOwnClub();
            deleteClub(club);

        } else {
            throw new IllegalStateException("У пользователя нет своего клуба.");
        }
    }

    @Override
    @Transactional
    public void adminDeleteClub(long clubId) {

        Club club = clubRepo.findClubById(clubId);
        deleteClub(club);

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

    private void changeClubToClubDtoData(Club club, ClubDto clubDto) {

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

    }

    private void deleteClub(Club club) {

        User owner = club.getOwner();
        clubRepo.delete(club);


        if (owner != null) {
            owner.setOwnClub(null);
            userRepo.save(owner);
        }

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

}
