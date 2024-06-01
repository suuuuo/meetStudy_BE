package com.elice.meetstudy.domain.qna.domain;

import com.elice.meetstudy.domain.audit.BaseEntity;
import com.elice.meetstudy.domain.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false,columnDefinition = "ENUM('PENDING','COMPLETED') DEFAULT 'PENDING'")
    private AnswerStatus answerStatus;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @NotNull
    String title;

    @NotNull
    String content;

    @Enumerated(EnumType.STRING)
    @NotNull
    private QuestionCategory questionCategory;

    boolean isSecret;

    String password;

    @Builder
    public Question( String title, String content,
        QuestionCategory questionCategory, boolean isSecret, String password) {
        this.title = title;
        this.content = content;
        this.questionCategory = questionCategory;
        this.isSecret = isSecret;
        this.password = password;
        this.answerStatus = AnswerStatus.PENDING;
    }

    public void update(String title, String content, QuestionCategory questionCategory,
        boolean isSecret, String password){
        this.title = title;
        this.content = content;
        this.questionCategory = questionCategory;
        this.isSecret = isSecret;
        this.password = password;
    }
}
