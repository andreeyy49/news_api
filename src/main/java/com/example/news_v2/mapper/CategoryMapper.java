package com.example.news_v2.mapper;

import com.example.news_v2.model.Category;
import com.example.news_v2.web.model.category.CategoryListResponse;
import com.example.news_v2.web.model.category.CategoryResponse;
import com.example.news_v2.web.model.category.UpsertCategoryRequest;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {NewsMapper.class})
public interface CategoryMapper {

    Category requestToCategory(UpsertCategoryRequest request);

    @Mapping(source = "categoryId", target = "id")
    Category requestToCategory(Long categoryId, UpsertCategoryRequest request);

    CategoryResponse categoryToResponse(Category category);

    default CategoryListResponse categoryListToCategoryResponseList(List<Category> categories){
        CategoryListResponse response = new CategoryListResponse();

        response.setCategories(categories.stream()
                .map(this::categoryToResponse)
                .collect(Collectors.toList()));

        return response;
    }
}
