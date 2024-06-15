package com.elice.meetstudy.domain.scrap.repository;

import com.elice.meetstudy.domain.scrap.domain.Scrap;
import com.elice.meetstudy.domain.scrap.dto.ScrapCategoryResponseDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {
  // 서연님이 작성
  List<Scrap> findScrapsByUserId(Long userId);

  // 이하영
  // 1. 게시글 스크랩 (insert into scrap)
  // 1-1. 이미 스크랩한 게시글인지 확인
  Optional<Scrap> findByUserIdAndPostId(@Param("userId") Long userId, @Param("postId") Long postId);

  // 2. 게시글 스크랩 취소 (delete scrap)
  @Modifying
  @Query("DELETE FROM Scrap s WHERE s.user.id = :userId AND s.post.id = :postId")
  void deleteByUserIdAndPostId(@Param("userId") Long userId, @Param("postId") Long postId);

  // 3. 게시글 스크랩 목록 조회

  // 4. 게시판 즐겨찾기
  // 4-1. 이미 스크랩한 게시판인지 확인
  Optional<Scrap> findByUserIdAndCategoryId(
      @Param("userId") Long userId, @Param("categoryId") Long categoryId);

  // 5. 게시판 즐겨찾기 취소
  @Modifying
  @Query("DELETE FROM Scrap s WHERE s.user.id = :userId AND s.category.id = :categoryId")
  void deleteByUserIdAndCategoryId(@Param("userId") Long postId, @Param("categoryId") Long userId);

  // 6. 게시판 즐겨찾기 목록 조회
  @Query(
      "SELECT NEW com.elice.meetstudy.domain.scrap.dto.ScrapCategoryResponseDTO(c.name AS category, s.createdAt AS createdAt) FROM Category c INNER JOIN Scrap s ON c.id = s.category.id WHERE s.user.id = ?1")
  List<ScrapCategoryResponseDTO> findCategoryAndCreatedAtByUserId(Long userId);

  //  List<Scrap> findAllByUserId(@Param("userId") Long userId, Pageable defaultPageable);

  // 7. 전체 스크랩 목록 조회 (이거 필요한가)

}
