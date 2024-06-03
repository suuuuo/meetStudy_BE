package com.elice.meetstudy.domain.chatroom.domain;

import com.elice.meetstudy.domain.studyroom.entity.StudyRoom;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

@Entity
@Getter
public class ChatRoom {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "chat_room_id")
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "study_room_id")
  private StudyRoom studyRoom;

  @OneToMany(mappedBy = "chatRoom")
  private List<Message> messages = new ArrayList<>();

  @Column(name="notice")
  private String notice;


  @Builder
  public ChatRoom(StudyRoom studyRoom) {
    this.studyRoom = studyRoom;
  }

  public void updateNotice(String notice){
    this.notice =notice;
  }
}