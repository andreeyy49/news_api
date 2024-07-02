package com.example.news_v2.web.model.news;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpsertNewsRequest {

    @NotNull(message = "ID категории должно быть указано!")
    @Positive(message = "ID категории должно быть больше 0!")
    private Long categoryId;

    @NotBlank(message = "Название новости должно быть указано!")
    private String title;

    @NotBlank(message = "Тело новости должно быть заполнено!")
    private String body;
}
