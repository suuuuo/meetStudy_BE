package com.elice.meetstudy.domain.scrap.service;

import com.elice.meetstudy.domain.category.entity.Category;
import com.elice.meetstudy.domain.post.domain.Post;
import com.elice.meetstudy.domain.scrap.domain.Scrap;
import com.elice.meetstudy.domain.scrap.dto.ScrapResponseDTO;
import com.elice.meetstudy.domain.scrap.repository.ScrapRepository;
import com.elice.meetstudy.domain.user.service.UserService;
import com.elice.meetstudy.util.EntityFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ScrapService {

  private final ScrapRepository scrapRepository;
  private final UserService userService;
  private final EntityFinder entityFinder;

  /** 게시글 스크랩 */
  public ScrapResponseDTO scrapPost(Long postId) {
    Long userId = 13L;
    //    User user = entityFinder.getUser();
    Post post = entityFinder.findPost(postId);

    // Scrap 엔티티 생성
    Scrap newScrap =
        Scrap.builder()
            .userId(userId)
            // category(category)
            .post(post)
            .build();

    return new ScrapResponseDTO(scrapRepository.save(newScrap));
  }

  /** 게시글 스크랩 삭제 - (이미 삭제되었어도 204) */
  public void scrapDeletePost(Long scrapId) {
    // Long userId = entityFinder.getUserId();
    Long userId = 13L;
    scrapRepository.deleteByPostIdAndUserId(scrapId, userId);
  }

  /** 게시판 스크랩 */
  public ScrapResponseDTO scrapCategory(Long categoryId) {
    Long userId = 1L;
    //    User user = entityFinder.getUser();
    Category category = entityFinder.findCategoryById(categoryId);

    // Scrap 엔티티 생성
    Scrap newScrap = Scrap.builder().userId(userId).category(category).build();

    return new ScrapResponseDTO(scrapRepository.save(newScrap));
  }

  /** 게시판 스크랩 삭제 - (이미 삭제되었어도 204) */
  public void scrapDeleteCategory(Long scrapId) {
    // Long userId = entityFinder.getUserId();
    Long userId = 1L;
    scrapRepository.deleteByPostIdAndUserId(scrapId, userId);
  }

  /** 게시판 스크랩 목록 조회 */
//  public List<ScrapResponseDTO> getScrapAll(int page, int size) {
//    Pageable defaultPageable = PageRequest.of(page, size, Sort.by(Direction.DESC, "createdAt"));
//    Long userId = 1L;
//    List<Scrap> scrapList = scrapRepository.findAllByUserId(userId, defaultPageable);
//
//    return scrapList.stream().map(ScrapResponseDTO::new).collect(Collectors.toList());
//  }
}
