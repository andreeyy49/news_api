package com.example.news_v2.web.model.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpsertCommentRequest {

    @NotNull(message = "ID новости должно быть указано!")
    @Positive(message = "ID новости должно быть больше 0!")
    private Long newsId;

    @NotNull(message = "ID автора должно быть указано!")
    @Positive(message = "ID автора должно быть больше 0!")
    private Long userId;

    @NotBlank(message = "Текст комментария должен быть указан!")
    private String message;
}
