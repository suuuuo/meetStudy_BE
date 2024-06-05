package com.elice.meetstudy.domain.chatroom.domain;

import com.elice.meetstudy.domain.chatroom.dto.ChatRoomDto;
import com.elice.meetstudy.domain.studyroom.entity.StudyRoom;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
public class ChatRoom {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "chat_room_id")
  private Long id;

  @Column(name="title")
  private String title;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "study_room_id")
  private StudyRoom studyRoom;

  @OneToMany(mappedBy = "chatRoom")
  private List<Message> messages = new ArrayList<>();

  @Column(name="notice")
  private String notice;


  @Builder
  public ChatRoom(StudyRoom studyRoom,String title,String notice) {
    this.title = title;
    this.notice = notice;
    this.studyRoom = studyRoom;
  }

  public void updateNotice(String notice){
    this.notice =notice;
  }


}