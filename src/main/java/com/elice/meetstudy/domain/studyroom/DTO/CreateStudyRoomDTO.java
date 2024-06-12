package com.elice.meetstudy.domain.studyroom.DTO;

import com.elice.meetstudy.domain.category.dto.CategoryDto;
import com.elice.meetstudy.domain.category.entity.Category;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudyRoomDTO {

    @NotNull
    private String title;

    @NotNull
    private String description;

    @Min(value = 4, message = "최소 스터디룸 인원 수는 4인입니다.")
    @Max(value = 30, message = "최대 스터디룸 인원 수는 30인입니다.")
    @NotNull
    private Long userCapacity;

    @NotNull
    private Long categoryId;
}
