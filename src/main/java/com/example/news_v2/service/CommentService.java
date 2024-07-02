package com.example.news_v2.service;

import com.example.news_v2.aop.Author;
import com.example.news_v2.entity.Comment;
import com.example.news_v2.entity.News;
import com.example.news_v2.entity.User;
import com.example.news_v2.repository.CommentRepository;
import com.example.news_v2.utils.BeanUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final NewsService newsService;
    private final UserService userService;

    public Comment findById(Long id) {
        return commentRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                        "Комментарий с ID: {} не найден", id
                )));
    }

    public Comment save(Comment comments) {
        News news = newsService.findById(comments.getNews().getId());
        User user = userService.findById(comments.getUser().getId());

        comments.setNews(news);
        comments.setUser(user);

        return commentRepository.save(comments);
    }

    public Comment update(Comment comment) {
        News news = newsService.findById(comment.getNews().getId());
        User user = userService.findById(comment.getUser().getId());

        Comment existedComments = findById(comment.getId());

        BeanUtils.copyNotNullProperties(comment, existedComments);
        comment.setNews(news);
        comment.setUser(user);

        return commentRepository.save(comment);
    }

    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    public void deleteByIdIn(List<Long> ids) {
        commentRepository.deleteAllById(ids);
    }
}
