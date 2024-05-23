package com.elice.meetstudy.domain.admin.controller;

import com.elice.meetstudy.domain.category.dto.CategoryDto;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping(value = "/admin/category/add")
    public String createCategory(Model model) {
        model.addAttribute("categoryDto", new CategoryDto());
        return "category/category-add"; //카테고리 추가 페이지
    }


}