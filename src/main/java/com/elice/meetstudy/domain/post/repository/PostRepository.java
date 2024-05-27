package com.elice.meetstudy.domain.post.repository;

import com.elice.meetstudy.domain.category.entity.Category;
import com.elice.meetstudy.domain.post.domain.Post;
import com.elice.meetstudy.domain.post.dto.RequestPostEdit;
import java.util.List;
import java.util.Optional;
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
  // 게시글 수정시 외래키만 별도 업데이트하여 복잡성 해결
  @Query("UPDATE Post p "
      + "SET p.category.id = :categoryId "
      + "WHERE p.id = :id")
  void updateCategoryIdById(@Param("categoryId") Long categoryId, @Param("id") Long id);

  @Query("UPDATE Post p "
      + "SET p.user.id = :userId "
      + "WHERE p.id = :id")
  void updateUserIdById(@Param("userId") Long userId, @Param("id") Long id);

//  Optional<Post> findById(Long id);
//  void save(RequestPostEdit editPost);

  // 게시글 삭제

  // 게시글 조회

  // 특정 게시판 내 게시글 검색
  @Query("SELECT p FROM Post p WHERE (p.title LIKE %:keyword% OR p.content LIKE %:keyword%) AND p.category.id = :categoryId")
  List<Post> findByKeyword(@Param("keyword") String keyword, @Param("categoryId") Long categoryId);

  List<Post> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword);

}