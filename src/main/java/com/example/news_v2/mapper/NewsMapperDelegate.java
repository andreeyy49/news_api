package com.example.news_v2.mapper;

import com.example.news_v2.model.News;
import com.example.news_v2.service.CategoryService;
import com.example.news_v2.service.UserService;
import com.example.news_v2.web.model.news.NewsWithoutCommentsResponse;
import com.example.news_v2.web.model.news.UpsertNewsRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public abstract class NewsMapperDelegate implements NewsMapper {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Override
    public News requestToNews(UpsertNewsRequest request) {
        News news = new News();
        news.setTitle(request.getTitle());
        news.setBody(request.getBody());
        news.setCategory(categoryService.findById(request.getCategoryId()));
        news.setUser(userService.findById(request.getUserId()));

        return news;
    }

    @Override
    public NewsWithoutCommentsResponse newsToResponse(News news) {
        if (news == null) {
            return null;
        }

        NewsWithoutCommentsResponse response = new NewsWithoutCommentsResponse();
        response.setBody(news.getBody());
        response.setId(news.getId());
        response.setTitle(news.getTitle());
        response.setCommentSize(news.getComments().size());

        return response;
    }

    @Override
    public News requestToNews(Long newsId, UpsertNewsRequest request) {
        News news = requestToNews(request);
        news.setId(newsId);

        return news;
    }

    @Override
    public List<NewsWithoutCommentsResponse> newsListToResponseList(List<News> news) {
        if (news == null) {
            return null;
        }

        List<NewsWithoutCommentsResponse> list = new ArrayList<>(news.size());
        for (News news1 : news) {
            list.add(newsToResponse(news1));
        }

        return list;
    }
}
