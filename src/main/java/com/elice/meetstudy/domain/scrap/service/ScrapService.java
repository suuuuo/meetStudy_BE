package com.elice.meetstudy.domain.scrap.service;

import com.elice.meetstudy.domain.category.entity.Category;
import com.elice.meetstudy.domain.post.domain.Post;
import com.elice.meetstudy.domain.scrap.domain.Scrap;
import com.elice.meetstudy.domain.scrap.dto.ScrapCategoryResponseDTO;
import com.elice.meetstudy.domain.scrap.dto.ScrapPostResponseDTO;
import com.elice.meetstudy.domain.scrap.repository.ScrapRepository;
import com.elice.meetstudy.domain.studyroom.exception.EntityNotFoundException;
import com.elice.meetstudy.domain.user.service.UserService;
import com.elice.meetstudy.util.EntityFinder;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
  public ScrapPostResponseDTO scrapPost(Long postId) {
    Post post = entityFinder.findPost(postId);
    Optional<Scrap> scrapPost = entityFinder.findScrapPost(postId);

    if (scrapPost.isPresent()) {
      throw new EntityNotFoundException("이미 스크랩한 게시글입니다.");
    }
    // Scrap 엔티티 생성
    Scrap newScrap = Scrap.builder().user(entityFinder.getUser()).post(post).build();

    return new ScrapPostResponseDTO(scrapRepository.save(newScrap));
  }

  /** 게시글 스크랩 삭제 - (이미 삭제되었어도 204) */
  public void scrapDeletePost(Long postId) {
    scrapRepository.deleteByUserIdAndPostId(entityFinder.getUser().getId(), postId);
  }

  /** 게시판 스크랩 */
  public ScrapCategoryResponseDTO scrapCategory(Long categoryId) {
    Category category = entityFinder.findCategoryById(categoryId);
    Optional<Scrap> scrapCategory = entityFinder.findScrapCategory(categoryId);

    if (scrapCategory.isPresent()) {
      throw new EntityNotFoundException("이미 스크랩한 게시판입니다.");
    }
    // Scrap 엔티티 생성
    Scrap newScrap = Scrap.builder().user(entityFinder.getUser()).category(category).build();

    return new ScrapCategoryResponseDTO(scrapRepository.save(newScrap));
  }

  /** 게시판 스크랩 삭제 - (이미 삭제되었어도 204) */
  public void scrapDeleteCategory(Long categoryId) {
    scrapRepository.deleteByUserIdAndCategoryId(entityFinder.getUser().getId(), categoryId);
  }

  /** 게시판 스크랩 목록 조회 */
  public List<ScrapCategoryResponseDTO> getScrapAll(int page, int size) {
    Pageable defaultPageable = PageRequest.of(page, size, Sort.by(Direction.DESC, "createdAt"));
    List<ScrapCategoryResponseDTO> scrapList =
        scrapRepository.findCategoryAndCreatedAtByUserId(entityFinder.getUser().getId());

    return scrapList;
  }
}
