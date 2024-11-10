package com.example.EventsOrganizer.controller.Club;


import com.example.EventsOrganizer.model.dto.ClubDto;
import com.example.EventsOrganizer.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/clubs")
public class ClubAdminController {

    private final ClubService clubService;

    @PatchMapping("/{clubId}")
    public ClubDto editClub(@RequestBody ClubDto clubDto, @PathVariable long clubId) {
        return clubService.updateClub(clubId, clubDto);
    }

    @DeleteMapping("/{clubId}")
    public void deleteClub(@PathVariable long clubId) {
         clubService.adminDeleteClub(clubId);
    }

}
