package com.example.news_v2.web.controller;

import com.example.news_v2.mapper.CategoryMapper;
import com.example.news_v2.entity.Category;
import com.example.news_v2.service.CategoryService;
import com.example.news_v2.web.model.category.CategoryListResponse;
import com.example.news_v2.web.model.category.CategoryResponse;
import com.example.news_v2.web.model.category.UpsertCategoryRequest;
import com.example.news_v2.web.model.filter.ObjectFilter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<CategoryListResponse> findAll(@Valid ObjectFilter filter){
        return ResponseEntity.ok(
                categoryMapper.categoryListToCategoryResponseList(
                        categoryService.findAll(filter)
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(
                categoryMapper.categoryToResponse(
                        categoryService.findById(id)
                )
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<CategoryResponse> create(@RequestBody @Valid UpsertCategoryRequest request){
        Category newCategory = categoryService.save(categoryMapper.requestToCategory(request));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryMapper.categoryToResponse(newCategory));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<CategoryResponse> update(@PathVariable("id") Long categoryId,
                                                   @RequestBody @Valid UpsertCategoryRequest request){
        Category updatedCategory = categoryService.update(categoryMapper.requestToCategory(categoryId, request));

        return ResponseEntity.ok(
                categoryMapper.categoryToResponse(updatedCategory)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<Void> delete(@PathVariable("id") Long categoryId){
        categoryService.deleteById(categoryId);

        return ResponseEntity.noContent().build();
    }
}