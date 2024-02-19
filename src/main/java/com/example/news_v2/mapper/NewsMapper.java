package com.example.news_v2.mapper;

import com.example.news_v2.model.News;
import com.example.news_v2.web.model.news.NewsListResponse;
import com.example.news_v2.web.model.news.NewsWithCommentsResponse;
import com.example.news_v2.web.model.news.NewsWithoutCommentsResponse;
import com.example.news_v2.web.model.news.UpsertNewsRequest;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@DecoratedWith(NewsMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = CommentMapper.class)
public interface NewsMapper {

    News requestToNews(UpsertNewsRequest request);

    @Mapping(source = "newsId", target = "id")
    News requestToNews(Long newsId, UpsertNewsRequest request);
    NewsWithCommentsResponse newsWhitCommentsToResponse(News news);

    NewsWithoutCommentsResponse newsToResponse(News news);

    List<NewsWithoutCommentsResponse> newsListToResponseList(List<News> news);

    default NewsListResponse newsListToNewsListResponse(List<News> news){
        NewsListResponse response = new NewsListResponse();

        response.setNews(news.stream()
                .map(this::newsToResponse)
                .collect(Collectors.toList()));

        return response;
    }
}
