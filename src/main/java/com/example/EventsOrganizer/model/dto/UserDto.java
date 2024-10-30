package com.example.EventsOrganizer.model.dto;

import lombok.*;




@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {

    private Long id;
    private String name;


}
