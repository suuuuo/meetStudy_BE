package com.elice.meetstudy.domain.post.repository;

import com.elice.meetstudy.domain.category.entity.Category;
import com.elice.meetstudy.domain.post.domain.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

  // 전체 게시판 내 게시글 검색
  @Query("SELECT p "
      + "FROM Post p "
      + "WHERE p.title "
      + "LIKE %:keyword% OR p.content LIKE %:keyword%")
  List<Post> findByKeyword(@Param("keyword") String keyword);

  // 카테고리별 게시글 조회 (선택한 게시판)
  List<Post> findByCategory(Category category);

  // 게시글 작성

  // 게시글 수정

  // 게시글 삭제

  // 게시글 조회

  // 특정 게시판 내 게시글 검색
  @Query("SELECT p FROM Post p WHERE (p.title LIKE %:keyword% OR p.content LIKE %:keyword%) AND p.category.id = :categoryId")
  List<Post> findByKeyword(@Param("keyword") String keyword, @Param("categoryId") Long categoryId);

  List<Post> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword);

}