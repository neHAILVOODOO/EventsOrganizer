package com.example.EventsOrganizer.model.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetEventDto {

    private long id;
    private String name;
    private String description;
    private LocalDate beginDate;
    private LocalDate endDate;
    private String status;

}
