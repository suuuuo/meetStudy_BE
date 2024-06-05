package com.elice.meetstudy.domain.scrap.repository;

import com.elice.meetstudy.domain.scrap.domain.Scrap;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {
  // 서연님이 작성
  List<Scrap> findScrapsByUserId(Long userId);

  // 이하영
  // 1. 게시글 스크랩 (insert into scrap)

  // 2. 게시글 스크랩 취소 (delete scrap)
  @Modifying
  @Query("DELETE FROM Scrap s WHERE s.post.id = :postId AND s.user.id = :userId")
  void deleteByPostIdAndUserId(@Param("postId") Long postId, @Param("userId") Long userId);

  // 3. 게시글 스크랩 목록 조회

  // 4. 게시판 즐겨찾기

  // 5. 게시판 즐겨찾기 취소

  // 6. 게시판 즐겨찾기 목록 조회
  //  @Query("SELECT s.category.id, s.createdAt FROM scrap s WHERE s.user.id = :userId")
  //  List<Scrap> findAllByUserId(@Param("userId") Long userId, Pageable defaultPageable);

  // 7. 전체 스크랩 목록 조회 (이거 필요한가)

}
