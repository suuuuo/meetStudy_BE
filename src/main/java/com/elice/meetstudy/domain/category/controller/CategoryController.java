package com.elice.meetstudy.domain.category.controller;

import com.elice.meetstudy.domain.category.dto.CategoryDto;
import com.elice.meetstudy.domain.category.entity.Category;
import com.elice.meetstudy.domain.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // 카테고리 조회
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.findAllCategories();
        return ResponseEntity.ok(categories);
    }

    // id에 해당하는 카테고리 조회
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.findCategory(id);
        return ResponseEntity.ok(category);
    }

    // 카테고리 생성
    @PostMapping("/add")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto categoryDto) {
        Category category = new Category(categoryDto.getName(), categoryDto.getDescription());
        Category createdCategory = categoryService.createCategory(category);
        return ResponseEntity.ok(createdCategory);
    }

    // 카테고리 수정
    @PutMapping("/{id}/edit")
    public ResponseEntity<Category> updateCategory(@RequestBody CategoryDto categoryDto,
                                                   @PathVariable Long id) {
        Category updatedCategory = new Category(categoryDto.getName(), categoryDto.getDescription());
        Category category = categoryService.updateCategory(updatedCategory, id);
        return ResponseEntity.ok(category);
    }

    // 카테고리 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}