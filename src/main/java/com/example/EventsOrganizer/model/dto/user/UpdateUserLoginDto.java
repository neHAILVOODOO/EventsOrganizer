package com.example.EventsOrganizer.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserLoginDto {

    @NotBlank(message = "Новый логин не может быть пустым")
    private String newLogin;
    @NotBlank(message = "Введите ваш пароль")
    private String password;

}
