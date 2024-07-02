package com.example.news_v2.web.model.user;

import com.example.news_v2.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class UpsertUserRequest {

    @NotBlank(message = "Имя пользователя должно быть указано!")
    @Size(min = 3, max = 30, message = "Имя пользователя не может быть меньше {min} и больше {max}!")
    private String username;

    @NotBlank(message = "Пароль пользователя должен быть указан!")
    @Size(min = 4, message = "Имя пользователя не может быть меньше {min}")
    private String password;
}
