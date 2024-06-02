package com.elice.meetstudy.domain.user.domain;

import com.elice.meetstudy.domain.studyroom.entity.UserStudyRoom;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

/**
 * user Entity
 */

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

  @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime createdAt;

  @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
  private LocalDateTime deletedAt;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Interest> interests = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<UserStudyRoom> userStudyRooms = new ArrayList<>();

  public void updateDeletedAt() {
    this.deletedAt = LocalDateTime.now();
  }

  @Builder
  public User(String email, String password, String username, String nickname, Role role){
    this.email = email;
    this.password = password;
    this.username = username;
    this.nickname = nickname;
    this.role = role;
  }

  public void addInterest(Interest interest) {
    interests.add(interest);
    interest.setUser(this);
  }

  public void addUserStudyRoom(UserStudyRoom userStudyRoom) {
    userStudyRooms.add(userStudyRoom);
    userStudyRoom.setUser(this);
  }

//    public void update(UserUpdateDto userUpdateDto){
//        this.password = userUpdateDto.getPassword();
//        this.username = userUpdateDto.getUsername();
//        this.nickname = userUpdateDto.getNickname();
//        this.interests = userUpdateDto.getInterests();
//    }
}
