package com.elice.meetstudy.domain.post.repository;

import com.elice.meetstudy.domain.post.domain.Post;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

  // 전체 게시판 내 게시글 검색
  @Query(
      "SELECT p "
          + "FROM Post p "
          + "WHERE p.title "
          + "LIKE %:keyword% OR p.content LIKE %:keyword%")
  List<Post> findByKeyword(@Param("keyword") String keyword);

  // 게시판 별 게시글 조회 - (공학게시판/교육게시판 등)
  List<Post> findByCategoryId(Long categoryId, Pageable pageable);

  // 게시글 작성

  // 게시글 수정

  // 게시글 삭제
  void deleteByIdAndUserId(Long postId, Long userId);

  // 내가 작성한 글 조회
  List<Post> findByUserId(Long userId, Pageable pageable);

  // 게시글 조회
  List<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

  @Query("SELECT p FROM Post p WHERE p.id = :id AND p.user.id = :userId")
  Post findByIdAndUserId(@Param("id") Long postId, @Param("userId") Long userId);

  // 조회수 증가
  @Modifying // 데이터를 수정하는 쿼리는 자동생성 X -> 어노테이션으로 명시적으로 나타내기
  @Query("UPDATE Post p SET p.hit = p.hit + 1 WHERE p.id = :id")
  void updateHit(Long id);

  // 특정 게시판 내 게시글 키워드 검색
  @Query(
      "SELECT p FROM Post p WHERE (p.title LIKE %:keyword% OR p.content LIKE %:keyword%) AND p.category.id = :categoryId")
  List<Post> findByKeyword(
      @Param("categoryId") Long categoryId, @Param("keyword") String keyword, Pageable pageable);

  // 전체 게시판 내 게시글 키워드 검색
  List<Post> findByTitleContainingOrContentContainingOrderByCreatedAtDesc(
      String titleKeyword, String contentKeyword, Pageable pageable);
}
