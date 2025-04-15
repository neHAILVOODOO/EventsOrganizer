package com.example.EventsOrganizer.model.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateEventInfoDto {

    private String name;
    private String description;
    private Date beginDate;
    private Date endDate;

}
