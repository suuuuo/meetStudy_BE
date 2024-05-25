package com.elice.meetstudy.domain.category.controller;

import com.elice.meetstudy.domain.category.entity.Category;
import com.elice.meetstudy.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class CategoryController {

    private CategoryService categoryService;

    // 카테고리 조회
    @GetMapping
    public String getCategories(Model model) {
        List<Category> categories = categoryService.findAllCategories();
        model.addAttribute("categories", categories);

        return "categories/list";
    }
}