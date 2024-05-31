package com.elice.meetstudy.domain.category.controller;

import com.elice.meetstudy.domain.category.entity.Category;
import com.elice.meetstudy.domain.category.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // 모든 카테고리 조회
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAllCategories());
    }

    // id에 해당하는 카테고리 조회
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findCategory(id));
    }

    // 카테고리 생성
    @PostMapping("/add")
    public ResponseEntity<Category> createCategory(@RequestBody String name) {
        return ResponseEntity.ok(categoryService.createCategory(name));
    }

    // 카테고리 수정
    @PutMapping("/{id}/edit")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id,
                                                   @RequestBody String newName) {
        return ResponseEntity.ok(categoryService.updateCategory(id, newName));
    }

    // 카테고리 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}