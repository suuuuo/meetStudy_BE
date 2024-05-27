package com.elice.meetstudy.domain.studyroom.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyRoom {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Date createdDate;
    private Long maxCapacity;

//    @ManyToOne
//    @JoinColumn(name = "category_id")
//    private Long categoryId;

}
