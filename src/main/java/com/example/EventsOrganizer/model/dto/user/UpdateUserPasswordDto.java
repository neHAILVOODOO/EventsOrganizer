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
public class UpdateUserPasswordDto {

    @NotBlank(message = "Введите текущий пароль")
    private String currentPassword;
    @NotBlank(message = "Введите новый пароль")
    private String newPassword;
    @NotBlank(message = "Введите повторно новый пароль для подтверждения")
    private String confirmNewPassword;

}
