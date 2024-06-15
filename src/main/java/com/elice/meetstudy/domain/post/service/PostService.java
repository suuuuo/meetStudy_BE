package com.elice.meetstudy.domain.post.service;

import com.elice.meetstudy.domain.category.entity.Category;
import com.elice.meetstudy.domain.post.domain.Post;
import com.elice.meetstudy.domain.post.dto.PostEditDTO;
import com.elice.meetstudy.domain.post.dto.PostResponseDTO;
import com.elice.meetstudy.domain.post.dto.PostWriteDTO;
import com.elice.meetstudy.domain.post.repository.PostRepository;
import com.elice.meetstudy.domain.studyroom.exception.EntityNotFoundException;
import com.elice.meetstudy.domain.user.service.UserService;
import com.elice.meetstudy.util.EntityFinder;
import java.util.List;
import java.util.stream.Collectors;
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
public class PostService {

  private final PostRepository postRepository;
  private final UserService userService;
  private final EntityFinder entityFinder;

  /** 게시글 작성 */
  public PostResponseDTO write(PostWriteDTO postCreate) {

    Category category = entityFinder.findCategoryById(postCreate.getCategoryId());

    Post newPost =
        Post.builder()
            .user(entityFinder.getUser())
            .category(category)
            .title(postCreate.getTitle().trim())
            .content(postCreate.getContent().trim())
            .build();

    return new PostResponseDTO(postRepository.save(newPost));
  }

  /** 특정 게시판에서 게시글 작성 */
  public PostResponseDTO writeByCategory(Long categoryId, PostWriteDTO postCreate) {

    Category category = entityFinder.findCategoryById(categoryId);

    // Post 엔티티 생성
    Post newPost =
        Post.builder()
            .user(entityFinder.getUser())
            .category(category)
            .title(postCreate.getTitle().trim())
            .content(postCreate.getContent().trim())
            .build();

    return new PostResponseDTO(postRepository.save(newPost));
  }

  /** 게시글 수정 */
  public PostResponseDTO edit(Long postId, PostWriteDTO editRequest) {

    Post post = entityFinder.findPost(postId);
    Category category = entityFinder.findCategoryById(editRequest.getCategoryId());

    if (entityFinder.getUser().getId() != post.getUser().getId()) {
      throw new EntityNotFoundException("해당 게시글을 수정할 권한이 없습니다");
    } else {
      PostEditDTO editPost =
          post.toEdit()
              .categoryId(
                  editRequest.getCategoryId() != null
                      ? editRequest.getCategoryId()
                      : post.getCategory().getId())
              .title(
                  editRequest.getTitle() != null
                      ? editRequest.getTitle().trim()
                      : post.getTitle().trim())
              .content(
                  editRequest.getContent() != null
                      ? editRequest.getContent().trim()
                      : post.getContent().trim())
              .build();

      // post 엔티티 수정
      post.edit(editPost, category);

      return new PostResponseDTO(postRepository.save(post));
    }
  }

  /** 게시글 삭제 - (이미 삭제된 게시글이어도 204) */
  public void delete(Long postId) {
    if (entityFinder.getUser().getId() != entityFinder.findPost(postId).getUser().getId()) {
      throw new EntityNotFoundException("해당 게시글을 삭제할 권한이 없습니다");
    }
    postRepository.deleteByIdAndUserId(postId, entityFinder.getUser().getId());
  }

  /** 게시판 별 게시글 조회 - (공학게시판/교육게시판 등) */
  public List<PostResponseDTO> getPostBycategory(Long categoryId, int page, int size) {
    Pageable defaultPageable = PageRequest.of(page, size, Sort.by(Direction.DESC, "createdAt"));

    return postRepository.findByCategoryId(categoryId, defaultPageable).stream()
        .map(PostResponseDTO::new)
        .collect(Collectors.toList());
  }

  /** 내가 작성한 글 조회 - (최근 작성된 순으로) */
  public List<PostResponseDTO> getPostByUser(int page, int size) {
    Pageable defaultPageable = PageRequest.of(page, size, Sort.by(Direction.DESC, "createdAt"));

    return postRepository.findByUserId(entityFinder.getUser().getId(), defaultPageable).stream()
        .map(PostResponseDTO::new)
        .collect(Collectors.toList());
  }

  /** 전체 게시글 조회 - (최근 작성된 순으로) */
  public List<PostResponseDTO> getPostAll(int page, int size) {
    Pageable defaultPageable = PageRequest.of(page, size, Sort.by(Direction.DESC, "createdAt"));
    return postRepository
        .findAllByOrderByCreatedAtDesc(defaultPageable)
        .stream() /* Page<Post> 객체를 스트림으로 변환 */
        .map(PostResponseDTO::new) /* 스트림 내 Post 객체 -> ResponsePostGet 객체로 변환. */
        .collect(Collectors.toList()); /* ResponsePostGet을 리스트로 반환 */
  }

  /** 게시글 상세 조회(postId) - (사용자가 게시글 제목을 클릭했을때) */
  public PostResponseDTO getPost(Long postId) {
    Post post = entityFinder.findPost(postId);

    // 조회수 증가
    postRepository.updateHit(postId);

    return PostResponseDTO.builder()
        .id(post.getId())
        .category(post.getCategory().getName())
        .nickname(post.getUser().getNickname())
        .title(post.getTitle())
        .content(post.getContent())
        .hit(post.getHit())
        .createdAt(post.getCreatedAt())
        .build();
  }

  /** 전체 게시판 내 게시글 검색 */
  public List<PostResponseDTO> searchPostInBoard(
      Long categoryId, String keyword, int page, int size) {
    Pageable defaultPageable = PageRequest.of(page, size, Sort.by(Direction.DESC, "createdAt"));
    return postRepository.findByKeyword(categoryId, keyword, defaultPageable).stream()
        .map(PostResponseDTO::new)
        .collect(Collectors.toList());
  }

  /** 전체 게시판 내 게시글 검색 */
  public List<PostResponseDTO> searchPost(String keyword, int page, int size) {
    Pageable defaultPageable = PageRequest.of(page, size, Sort.by(Direction.DESC, "createdAt"));
    return postRepository
        .findByTitleContainingOrContentContainingOrderByCreatedAtDesc(
            keyword, keyword, defaultPageable)
        .stream()
        .map(PostResponseDTO::new)
        .collect(Collectors.toList());
  }
}
