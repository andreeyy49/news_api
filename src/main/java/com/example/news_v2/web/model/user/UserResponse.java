package com.example.news_v2.web.model.user;

import com.example.news_v2.web.model.comment.CommentResponse;
import com.example.news_v2.web.model.news.NewsWithCommentsResponse;
import com.example.news_v2.web.model.news.NewsWithoutCommentsResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserResponse {

    private Long id;

    private String name;

    private List<NewsWithoutCommentsResponse> news = new ArrayList<>();

    private List<CommentResponse> comments = new ArrayList<>();
}
