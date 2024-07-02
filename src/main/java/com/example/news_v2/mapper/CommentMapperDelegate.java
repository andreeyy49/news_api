package com.example.news_v2.mapper;

import com.example.news_v2.entity.Comment;
import com.example.news_v2.service.NewsService;
import com.example.news_v2.service.UserService;
import com.example.news_v2.web.model.comment.UpsertCommentRequest;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CommentMapperDelegate implements CommentMapper{

    @Autowired
    private NewsService newsService;

    @Override
    public Comment requestToComment(UpsertCommentRequest request) {
        Comment comment = new Comment();
        comment.setMessage(request.getMessage());
        comment.setNews(newsService.findById(request.getNewsId()));

        return comment;
    }

    @Override
    public Comment requestToComment(Long commentsId, UpsertCommentRequest request) {
        Comment comment = requestToComment(request);
        comment.setId(commentsId);

        return comment;
    }
}
