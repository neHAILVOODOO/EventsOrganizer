package com.example.EventsOrganizer.controller.Club;


import com.example.EventsOrganizer.model.dto.ClubDto;
import com.example.EventsOrganizer.service.ClubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/clubs")
@Slf4j
public class ClubAdminController {

    private final ClubService clubService;

    @PatchMapping("/{clubId}")
    public ClubDto editClub(@RequestBody ClubDto clubDto, @PathVariable long clubId, HttpServletRequest request) {

        log.info("Получен запрос на вывод информации о клубе id{}, {}", clubId, request.getRequestURI());
        return clubService.updateClub(clubId, clubDto);

    }

    @DeleteMapping("/{clubId}")
    public void deleteClub(@PathVariable long clubId, HttpServletRequest request) {

        log.info("Получен запрос на удаление клуба id{} {}", clubId, request.getRequestURI());
         clubService.adminDeleteClub(clubId);

    }

}
