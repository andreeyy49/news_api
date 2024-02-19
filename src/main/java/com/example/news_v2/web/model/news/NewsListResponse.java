package com.example.news_v2.web.model.news;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NewsListResponse {

    private List<NewsWithoutCommentsResponse> news = new ArrayList<>();
}
