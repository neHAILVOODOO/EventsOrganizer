package com.example.EventsOrganizer.model.dto.user;

import com.example.EventsOrganizer.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetUserDto {

    private long id;
    private String name;
    private String surname;
    private String phoneNumber;
    private int age;
    private List<Role> roles;


}
