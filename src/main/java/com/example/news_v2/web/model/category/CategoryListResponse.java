package com.example.news_v2.web.model.category;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryListResponse {

    private List<CategoryResponse> categories = new ArrayList<>();
}
