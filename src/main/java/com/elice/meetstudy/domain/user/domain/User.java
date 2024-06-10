package com.elice.meetstudy.domain.user.domain;

import com.elice.meetstudy.domain.chatroom.domain.Message;
import com.elice.meetstudy.domain.studyroom.entity.UserStudyRoom;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

/** user Entity */
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false, length = 50)
  private String email;

  @Column(nullable = false, length = 100)
  private String password;

  @Column(nullable = false, length = 10)
  private String username;

  @Column(length = 10)
  private String nickname;

  @Column(
      name = "created_at",
      nullable = false,
      columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime createdAt;

  @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
  private LocalDateTime deletedAt;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "user_id")
  private List<Interest> interests = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<UserStudyRoom> userStudyRooms = new ArrayList<>();

  @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
  private List<Message> messages = new ArrayList<>();


  public void updateDeletedAt() {
    this.deletedAt = LocalDateTime.now();
  }

  @Builder
  public User(String email, String password, String username, String nickname, LocalDateTime createdAt, Role role, Long id) {
    this.email = email;
    this.password = password;
    this.username = username;
    this.nickname = nickname;
    this.createdAt = createdAt;
    this.role = role;
    this.id = id;
  }

  public void addInterest(Interest interest) {
    interests.add(interest);
  }

  public void clearInterests() {
    interests.clear();
  }

  public void addUserStudyRoom(UserStudyRoom userStudyRoom) {
    userStudyRooms.add(userStudyRoom);
    userStudyRoom.setUser(this);
  }
}
