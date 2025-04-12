package com.example.EventsOrganizer.model.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetEventForListDto {

    private long id;
    private String name;
    private long organizingClubId;
    private String organizingClubName;

}
