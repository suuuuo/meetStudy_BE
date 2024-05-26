package com.elice.meetstudy.domain.calendar.domain;

import com.elice.meetstudy.domain.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "calendar", cascade = CascadeType.REMOVE)
    private List<Calendar_detail> details;

    //스터디룸 id가 없는 캘린더 = 개인 캘린더, 있는 캘린더 = 공용 캘린더
//    @OneToOne
//    @JoinColumn(name = "study_room_id")
//    private Studyroom studyroom;

//    public Calendar(User user, Studyroom studyroom) {
//        this.user = user;
//        this.studyroom = studyroom;
//    }

    public Calendar(User user){
    this.user = user;
}
}
