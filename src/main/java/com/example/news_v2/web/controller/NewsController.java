package com.example.news_v2.web.controller;

import com.example.news_v2.aop.Author;
import com.example.news_v2.aop.ThisUser;
import com.example.news_v2.entity.User;
import com.example.news_v2.mapper.NewsMapper;
import com.example.news_v2.entity.News;
import com.example.news_v2.service.NewsService;
import com.example.news_v2.service.UserService;
import com.example.news_v2.web.model.filter.NewsFilter;
import com.example.news_v2.web.model.news.NewsListResponse;
import com.example.news_v2.web.model.news.NewsWithCommentsResponse;
import com.example.news_v2.web.model.news.NewsWithoutCommentsResponse;
import com.example.news_v2.web.model.news.UpsertNewsRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;
    private final UserService userService;
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
    public ResponseEntity<NewsWithoutCommentsResponse> create(@RequestBody @Valid UpsertNewsRequest request,
                                                              @AuthenticationPrincipal UserDetails userDetails) {
        News newNews = newsMapper.requestToNews(request);
        User user =  userService.findByUsername(userDetails.getUsername());
        newNews.setUser(user);
        newNews = newsService.save(newNews);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                newsMapper.newsToResponse(
                        newNews
                )
        );
    }

    @Author
    @PutMapping("/{id}")
    public ResponseEntity<NewsWithoutCommentsResponse> update(@PathVariable("id") Long newsId,
                                                              @RequestBody @Valid UpsertNewsRequest request,
                                                              @AuthenticationPrincipal UserDetails userDetails){
        News updatedNews = newsMapper.requestToNews(newsId, request);
        User user =  userService.findByUsername(userDetails.getUsername());
        updatedNews.setUser(user);
        updatedNews = newsService.update(updatedNews);

        return ResponseEntity.ok(
                newsMapper.newsToResponse(updatedNews)
        );
    }

    @ThisUser
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        newsService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
