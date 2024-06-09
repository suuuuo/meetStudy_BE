package com.elice.meetstudy.domain.chatroom.domain;

import com.elice.meetstudy.domain.studyroom.entity.StudyRoom;
import com.elice.meetstudy.domain.user.domain.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class ChatRoom {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "chat_room_id")
  private Long id;

  @Column(name = "title")
  private String title;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "study_room_id")
  private StudyRoom studyRoom;

  @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Message> messages = new ArrayList<>();

  @Column(name = "notice")
  private String notice;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User chatAdmin;

  @Builder
  public ChatRoom(StudyRoom studyRoom, User chatAdmin, String title, String notice) {
    this.title = title;
    this.notice = notice;
    this.studyRoom = studyRoom;
    this.chatAdmin = chatAdmin;
  }

  public void updateNotice(String notice) {
    this.notice = notice;
  }


  public void changeChatAdmin(User user) {
    this.chatAdmin = user;
  }
}
