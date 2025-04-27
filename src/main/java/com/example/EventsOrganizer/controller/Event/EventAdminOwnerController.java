package com.example.EventsOrganizer.controller.Event;

import com.example.EventsOrganizer.model.dto.event.UpdateEventInfoDto;
import com.example.EventsOrganizer.security.UserPrincipal;
import com.example.EventsOrganizer.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
@Slf4j
public class EventAdminOwnerController {

    private final EventService eventService;


    //и админ тоже (удалить userPrincipal)
    //ну собственно сделать проверку, что либо админ либо хозяин, но это аннотацию создать надо
    @PatchMapping("/{eventId}")
    public ResponseEntity<Void> editEvent(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody UpdateEventInfoDto updateEventInfoDto,
            @PathVariable long eventId,
            HttpServletRequest request
    ) {
        log.info("Получен запрос на изменение информации о мероприятии id{} пользователем id{} {}", eventId, userPrincipal.getUserId(), request.getRequestURI());
        eventService.updateEventInfo(updateEventInfoDto, eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    //и админ тоже
    //удалить userPrincipal
    @DeleteMapping("/{eventId}")
    public void deleteEvent(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable long eventId,
            HttpServletRequest request
    ) {

        log.info("Получен запрос на удаление мероприятия id{} пользователем id{} {}", eventId, userPrincipal.getUserId(), request.getRequestURI());
        eventService.deleteEvent(eventId);

    }


}
