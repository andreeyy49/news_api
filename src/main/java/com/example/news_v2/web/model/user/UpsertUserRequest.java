package com.example.news_v2.web.model.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpsertUserRequest {

    @NotBlank(message = "Имя пользователя должно быть указано!")
    @Size(min = 3, max = 30, message = "Имя пользователя не может быть меньше {min} и больше {max}!")
    private String name;
}
