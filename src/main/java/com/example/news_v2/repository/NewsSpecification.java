package com.example.news_v2.repository;

import com.example.news_v2.entity.Category;
import com.example.news_v2.entity.News;
import com.example.news_v2.entity.User;
import com.example.news_v2.web.model.filter.NewsFilter;
import org.springframework.data.jpa.domain.Specification;

public interface NewsSpecification {

    static Specification<News> withFilter(NewsFilter newsFilter){
        return Specification.where(byCategoryTitle(newsFilter.getCategoryTitle()))
                .and(byAuthorId(newsFilter.getAuthorId()));
    }

    static Specification<News> byCategoryTitle(String categoryTitle){
        return (root, query, criteriaBuilder) -> {
            if(categoryTitle == null){
                return null;
            }

            return criteriaBuilder.equal(root.get("category").get(Category.Fields.title), categoryTitle);
        };
    }

    static Specification<News> byAuthorId(Long authorId){
        return (root, query, criteriaBuilder) -> {
            if(authorId == null){
                return null;
            }

            return criteriaBuilder.equal(root.get("user").get(User.Fields.id), authorId);
        };
    }
}
