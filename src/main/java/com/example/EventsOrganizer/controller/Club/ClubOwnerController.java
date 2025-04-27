package com.example.EventsOrganizer.controller.Club;

import com.example.EventsOrganizer.model.dto.club.CreateClubDto;
import com.example.EventsOrganizer.model.dto.club.UpdateClubDto;
import com.example.EventsOrganizer.model.dto.event.CreateEventDto;
import com.example.EventsOrganizer.model.dto.event.UpdateEventInfoDto;
import com.example.EventsOrganizer.security.UserPrincipal;
import com.example.EventsOrganizer.service.ClubService;
import com.example.EventsOrganizer.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

//ПЕРЕДЕЛАТЬ ТАК, ЧТОБЫ ЭТИ МЕТОДЫ БЫЛИ ВОЗМОЖНЫ И ДЛЯ АДМИНА

@RestController
@RequestMapping("/owner/club")
@RequiredArgsConstructor
@Slf4j
public class ClubOwnerController {

    private final ClubService clubService;


    //и админ тоже
    //удалить userPrincipal и сделать по id в сервисе

    @PatchMapping()
    public ResponseEntity<Void> editClub(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody UpdateClubDto updateClubDto,
            HttpServletRequest request
    ) {

        log.info("Получен запрос на изменение информации о клубе пользователем id{} {}", userPrincipal.getUserId(), request.getRequestURI());
        clubService.updateClub(userPrincipal.getUserId(), updateClubDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //и админ тоже
    //удалить userPrincipal и сделать по id в сервисе

    @DeleteMapping()
    public void deleteClub(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            HttpServletRequest request
    ) {
        log.info("Получен запрос на удаление клуба пользователем id{} {}", userPrincipal.getUserId(), request.getRequestURI());
        clubService.deleteClub(userPrincipal.getUserId());
    }



}
