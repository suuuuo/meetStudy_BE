package com.elice.meetstudy.domain.category.service;

import com.elice.meetstudy.domain.category.dto.CategoryDto;
import com.elice.meetstudy.domain.category.entity.Category;
import com.elice.meetstudy.domain.category.repository.CategoryRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // 모든 카테고리를 조회
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    // id에 해당하는 카테고리 조회
    public Category findCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    // 카테고리 생성
    public Category createCategory(String name, String description) {
        Category category = new Category(name, description);
        return categoryRepository.save(category);
    }

    // 카테고리 수정
    public Category updateCategory(Long id, String newName, String newDescription) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        category.setName(newName);
        category.setDescription(newDescription);
        return categoryRepository.save(category);
    }

    // 카테고리 삭제
    public void deleteCategory(Long categoryId) {
        Category foundCategory = categoryRepository.findById(categoryId)
                .orElseThrow(EntityNotFoundException::new);

        categoryRepository.delete(foundCategory);
    }
}