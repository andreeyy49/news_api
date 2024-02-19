package com.example.news_v2.web.model.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpsertCategoryRequest {

    @NotBlank(message = "Название категории должно быть заполнено!")
    @Size(min = 3, max = 30, message = "Название категории не может быть меньше {min} и больше {max}!")
    private String title;
}
