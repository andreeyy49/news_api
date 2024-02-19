package com.example.news_v2.web.model.news;

import lombok.Data;

@Data
public class NewsWithoutCommentsResponse {

    private Long id;

    private String title;

    private String body;

    private Integer commentSize;
}
