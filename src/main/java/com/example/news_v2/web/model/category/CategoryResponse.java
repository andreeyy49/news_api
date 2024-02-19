package com.example.news_v2.web.model.category;

import com.example.news_v2.web.model.news.NewsWithCommentsResponse;
import com.example.news_v2.web.model.news.NewsWithoutCommentsResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryResponse {

    private Long id;

    private String title;

    private List<NewsWithoutCommentsResponse> news = new ArrayList<>();
}
