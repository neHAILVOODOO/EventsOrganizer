package com.example.EventsOrganizer.model.dto.user;

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
public class UserPreviewDto {

    private long id;
    private String name;
    private String surname;
    private String phoneNumber;

}
