package com.study.community.category;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public CategoryResponse create(CategoryCreateRequest request) {
        Category category = new Category(request.name());
        if (categoryRepository.existsByName(request.name())) {
            throw new DuplicateCategoryNameException(request.name());
        }

        Category saved = categoryRepository.save(category);
        return CategoryResponse.from(saved);
    }

    public List<CategoryResponse> list() {
        return categoryRepository.findAll().stream()
                .map(CategoryResponse::from)
                .toList();
    }

}
