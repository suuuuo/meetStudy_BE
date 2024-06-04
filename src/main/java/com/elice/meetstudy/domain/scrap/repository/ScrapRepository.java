package com.elice.meetstudy.domain.scrap.repository;

import com.elice.meetstudy.domain.scrap.domain.Scrap;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {

  List<Scrap> findScrapsByUserId(Long userId);

  // 1. 게시글 스크랩 (insert into scrap)

  // 2. 게시글 스크랩 취소 (delete scrap)
  // 3. 게시글 스크랩 목록 조회

  // 4. 게시판 즐겨찾기

  // 5. 게시판 즐겨찾기 취소

  // 6. 게시판 즐겨찾기 목록 조회

  // 7. 전체 스크랩 목록 조회 (이거 필요한가)

}
