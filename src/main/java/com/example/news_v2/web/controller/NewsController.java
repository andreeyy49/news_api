package com.example.news_v2.web.controller;

import com.example.news_v2.aop.Author;
import com.example.news_v2.mapper.NewsMapper;
import com.example.news_v2.model.News;
import com.example.news_v2.service.NewsService;
import com.example.news_v2.web.model.filter.NewsFilter;
import com.example.news_v2.web.model.news.NewsListResponse;
import com.example.news_v2.web.model.news.NewsWithCommentsResponse;
import com.example.news_v2.web.model.news.NewsWithoutCommentsResponse;
import com.example.news_v2.web.model.news.UpsertNewsRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;
    private final NewsMapper newsMapper;

    @GetMapping("/filter")
    public ResponseEntity<NewsListResponse> filterBy(@Valid NewsFilter filter) {
        return ResponseEntity.ok(
          newsMapper.newsListToNewsListResponse(
                  newsService.filterBy(filter)
          )
        );
    }

    @GetMapping
    public ResponseEntity<NewsListResponse> findAll(@Valid NewsFilter filter){
        return ResponseEntity.ok(
                newsMapper.newsListToNewsListResponse(
                        newsService.filterBy(filter)
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsWithCommentsResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(
                newsMapper.newsWhitCommentsToResponse(
                        newsService.findById(id)
                )
        );
    }

    @PostMapping
    public ResponseEntity<NewsWithoutCommentsResponse> create(@RequestBody @Valid UpsertNewsRequest request) {
        News newNews = newsService.save(newsMapper.requestToNews(request));

        return ResponseEntity.status(HttpStatus.CREATED).body(
                newsMapper.newsToResponse(
                        newNews
                )
        );
    }

    @Author
    @PutMapping("/{id}&{userId}")
    public ResponseEntity<NewsWithoutCommentsResponse> update(@PathVariable("id") Long newsId,
                                                              @RequestBody @Valid UpsertNewsRequest request){
        News updatedNews = newsService.update(newsMapper.requestToNews(newsId, request));

        return ResponseEntity.ok(
                newsMapper.newsToResponse(updatedNews)
        );
    }

    @Author
    @DeleteMapping("/{id}&{userId}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        newsService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
