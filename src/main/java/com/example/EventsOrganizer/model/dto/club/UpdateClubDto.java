package com.example.EventsOrganizer.model.dto.club;

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
public class UpdateClubDto {

    private String name;
    private String description;
    private String thematics;

}
