package com.elice.meetstudy.domain.category.service;

import com.elice.meetstudy.domain.category.entity.Category;
import com.elice.meetstudy.domain.category.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // 모든 카테고리를 DB에서 조회해 반환
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}