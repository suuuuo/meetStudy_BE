package com.elice.meetstudy.domain.qna.repository;

import com.elice.meetstudy.domain.qna.domain.Question;
import com.elice.meetstudy.domain.qna.domain.QuestionCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

  Page<Question> findAllByTitleContainingOrderByIdDesc(String title, Pageable pageable);

  Page<Question> findAllByTitleContainingAndQuestionCategoryOrderByIdDesc(
      String title, QuestionCategory questionCategory, Pageable pageable);

  Page<Question> findAllByQuestionCategoryOrderByIdDesc(
      Pageable pageable, QuestionCategory questionCategory);

  Page<Question> findAllByOrderByIdDesc(Pageable pageable);

  Question findByAnswerId(long answerId);
}
