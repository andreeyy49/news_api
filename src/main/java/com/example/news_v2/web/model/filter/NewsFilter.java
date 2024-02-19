package com.example.news_v2.web.model.filter;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewsFilter {
    @NotNull(message = "Размер страницы должен быть указан!")
    @Positive(message = "Размер страницы должен быть больше нуля!")
    private Integer pageSize;

    @NotNull(message = "Номер страницы должен быть указан!")
    @PositiveOrZero(message = "Номер страницы не должен быть меньше нуля!")
    private Integer pageNumber;

    private String categoryTitle;

    private Long authorId;
}
