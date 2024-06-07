package com.elice.meetstudy.domain.category.controller;

import com.elice.meetstudy.domain.category.dto.CategoryDto;
import com.elice.meetstudy.domain.category.entity.Category;
import com.elice.meetstudy.domain.category.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/categories")
@Tag(name = "D. 카테고리")
public class CategoryController {

  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  // 모든 카테고리 조회
  @Operation(summary = "모든 카테고리 조회")
  @GetMapping("/public")
  public ResponseEntity<List<Category>> getAllCategories() {
    return ResponseEntity.ok(categoryService.findAllCategories());
  }

  // id에 해당하는 카테고리 조회
  @Operation(summary = "id에 해당하는 카테고리 조회")
  @GetMapping("/public/{id}")
  public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
    return ResponseEntity.ok(categoryService.findCategory(id));
  }

  // 카테고리 생성
  @Operation(summary = "카테고리 생성")
  @PostMapping
  public ResponseEntity<Category> createCategory(@RequestBody CategoryDto categoryDto) {
    return ResponseEntity.ok(
        categoryService.createCategory(categoryDto.getName(), categoryDto.getDescription()));
  }

  // 카테고리 수정
  @Operation(summary = "카테고리 수정")
  @PutMapping("/{id}")
  public ResponseEntity<Category> updateCategory(
      @PathVariable Long id, @RequestBody CategoryDto categoryDto) {
    return ResponseEntity.ok(
        categoryService.updateCategory(id, categoryDto.getName(), categoryDto.getDescription()));
  }

  // 카테고리 삭제
  @Operation(summary = "카테고리 삭제")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
    categoryService.deleteCategory(id);
    return ResponseEntity.noContent().build();
  }
}
