package com.elice.meetstudy.domain.studyroom.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorBodyDTO {
    private Long code;
    private String description;


}
