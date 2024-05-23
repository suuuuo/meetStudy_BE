package com.elice.meetstudy.domain.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {

    @NotNull
    @NotBlank(message = "카테고리명은 필수 입력 값입니다.")
    private String name;

    @NotNull
    @NotBlank(message = "설명란은 필수 입력 값입니다.")
    private String description;
}