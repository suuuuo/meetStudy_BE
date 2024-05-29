package com.elice.meetstudy.domain.comment.repository;

import com.elice.meetstudy.domain.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

  // 댓글 작성

  // 댓글 수정

  // 댓글 삭제

  // 게시글에 달린 댓글 조회

  // 전체 댓글 조회

  // 특정 댓글 조회(commentId)

  // 전체 댓글 내 키워드 검색

}
