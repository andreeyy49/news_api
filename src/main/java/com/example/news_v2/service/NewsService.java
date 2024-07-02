package com.example.news_v2.service;

import com.example.news_v2.entity.Category;
import com.example.news_v2.entity.News;
import com.example.news_v2.entity.User;
import com.example.news_v2.repository.NewsRepository;
import com.example.news_v2.repository.NewsSpecification;
import com.example.news_v2.utils.BeanUtils;
import com.example.news_v2.web.model.filter.NewsFilter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;
    private final CategoryService categoriesService;
    private final UserService userService;

    public List<News> filterBy(NewsFilter filter){
        return newsRepository.findAll(NewsSpecification.withFilter(filter),
                PageRequest.of(
                        filter.getPageNumber(),
                        filter.getPageSize()
                )).getContent();
    }

    public News findById(Long id){
        return newsRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(MessageFormat.format(
                        "Новость с ID: {} не найдена", id
                )));
    }

    public News save(News news){
        Category category = categoriesService.findById(news.getCategory().getId());
        User user = userService.findById(news.getUser().getId());
        news.setCategory(category);
        news.setUser(user);

        return newsRepository.save(news);
    }

    public News update(News news){
        User user = userService.findById(news.getUser().getId());
        Category category = categoriesService.findById(news.getCategory().getId());
        News exisedNews = findById(news.getId());

        BeanUtils.copyNotNullProperties(news, exisedNews);
        news.setCategory(category);
        news.setUser(user);

        return newsRepository.save(news);
    }

    public void deleteById(Long id){
        newsRepository.deleteById(id);
    }

    public void deleteByIdIn(List<Long> ids){
        newsRepository.deleteAllById(ids);
    }
}
