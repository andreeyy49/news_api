package com.example.news_v2.service;

import com.example.news_v2.model.Category;
import com.example.news_v2.repository.CategoryRepository;
import com.example.news_v2.utils.BeanUtils;
import com.example.news_v2.web.model.filter.ObjectFilter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.naming.ldap.PagedResultsControl;
import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoriesRepository;

    public List<Category> findAll(ObjectFilter filter) {
        return categoriesRepository.findAll(
                PageRequest.of(
                        filter.getPageNumber(),
                        filter.getPageSize()
                )
        ).getContent();
    }

    public Category findById(Long id) {
        return categoriesRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format(
                "Категория с ID: {} не найдена", id
        )));
    }

    public Category save(Category category) {
        return categoriesRepository.save(category);
    }

    public Category update(Category category){
        Category existedCategory = findById(category.getId());

        BeanUtils.copyNotNullProperties(category, existedCategory);

        return categoriesRepository.save(category);
    }

    public void deleteById(Long id){
        categoriesRepository.deleteById(id);
    }
}
