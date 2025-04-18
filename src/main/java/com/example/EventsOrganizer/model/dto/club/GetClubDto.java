package com.example.EventsOrganizer.model.dto.club;

import com.example.EventsOrganizer.model.dto.user.UserPreviewDto;
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
public class GetClubDto {

    private long id;
    private String name;
    private String description;
    private String thematics;
    private UserPreviewDto owner;

}
