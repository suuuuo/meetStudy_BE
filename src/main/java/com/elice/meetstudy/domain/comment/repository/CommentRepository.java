package com.elice.meetstudy.domain.comment.repository;

import com.elice.meetstudy.domain.comment.domain.Comment;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

  // 댓글 작성

  // 댓글 수정

  // 댓글 삭제
  @Modifying
  @Query("DELETE FROM Comment c WHERE c.id = :commentId AND c.user.id = :userId")
  void deleteByIdAndUserId(@Param("commentId") Long commentId, @Param("userId") Long userId);

  // 게시글에 달린 댓글 조회
  Page<Comment> findAllByPostId(Long postId, Pageable pageable);

  // 전체 댓글 내 키워드 검색
  @Query("SELECT c FROM Comment c WHERE c.post.id = :postId AND c.content LIKE %:keyword%")
  List<Comment> findAllByPostIdAndKeyword(
      @Param("postId") Long postId, @Param("keyword") String keyword, Pageable pageable);

  // 전체 댓글 조회
  List<Comment> findAllByOrderByCreatedAtDesc(Pageable pageable);

  // 전체 댓글 내 키워드 검색
  @Query("SELECT c FROM Comment c WHERE c.content LIKE %:keyword% ORDER BY c.createdAt DESC")
  List<Comment> findAllByKeywordOrderByCreatedAtDesc(
      @Param("keyword") String keyword, Pageable pageable);

  // 특정 회원이 작성한 댓글 조회
  List<Comment> findAllByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
}
