package com.elice.meetstudy.domain.studyroom.entity;

import com.elice.meetstudy.domain.category.entity.Category;
import com.elice.meetstudy.domain.chatroom.domain.ChatRoom;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @OneToMany(mappedBy = "studyRoom", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserStudyRoom> userStudyRooms = new ArrayList<>();

  public void addUserStudyRoom(UserStudyRoom userStudyRoom) {
    userStudyRooms.add(userStudyRoom);
    userStudyRoom.setStudyRoom(this);
  }

  @OneToMany(mappedBy = "studyRoom")
  private List<ChatRoom> chatRooms = new ArrayList<>();
}
