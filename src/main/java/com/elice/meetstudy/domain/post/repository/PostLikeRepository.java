package com.elice.meetstudy.domain.post.repository;

import com.elice.meetstudy.domain.post.domain.PostLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

  // 게시글 좋아요 (GET)

  // 게시글 좋아요 취소 (DELETE)
  @Modifying
  @Query("DELETE FROM PostLike pl WHERE pl.user.id = :userId AND pl.post.id = :postId")
  void deleteByUserIdAndPostId(@Param("userId") Long userId, @Param("postId") Long postId);

  // 좋아요한 게시글인지 찾기
  Optional<PostLike> findByUserIdAndPostId(
      @Param("userId") Long userId, @Param("postId") Long postId);

  // 게시글 별 좋아요 수 조회
  @Query("SELECT COUNT(pl.id) FROM PostLike pl WHERE pl.post.id = :postId")
  long countByPostId(@Param("postId") Long postId);
}
