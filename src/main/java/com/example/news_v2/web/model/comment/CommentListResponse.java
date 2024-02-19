package com.example.news_v2.web.model.comment;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommentListResponse {

    List<CommentResponse> comments = new ArrayList<>();
}
