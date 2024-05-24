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
public class UserStudyRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date joinDate;
    private String permission;

    @ManyToOne
    @JoinColumn(name = "studyRoom_id")
    private Long studyRoomId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Long userId;


}
