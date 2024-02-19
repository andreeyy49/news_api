package com.example.news_v2.web.model.news;

import com.example.news_v2.web.model.comment.CommentResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NewsWithCommentsResponse {

    private Long id;

    private String title;

    private String body;

    private List<CommentResponse> comments = new ArrayList<>();
}
