package com.elice.meetstudy.domain.post.service;

import com.elice.meetstudy.domain.category.entity.Category;
import com.elice.meetstudy.domain.post.domain.Post;
import com.elice.meetstudy.domain.post.dto.PostEditDTO;
import com.elice.meetstudy.domain.post.dto.PostResponseDTO;
import com.elice.meetstudy.domain.post.dto.PostWriteDTO;
import com.elice.meetstudy.domain.post.repository.PostRepository;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.util.EntityFinder;
import com.elice.meetstudy.util.TokenUtility;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class PostService {

  private final PostRepository postRepository;
  private final EntityFinder entityFinder;
  private final TokenUtility tokenUtility;

  @Autowired
  public PostService(
      PostRepository postRepository, EntityFinder entityFinder, TokenUtility tokenUtility) {
    this.postRepository = postRepository;
    this.entityFinder = entityFinder;
    this.tokenUtility = tokenUtility;
  }

  /** 게시글 작성 */
  public PostResponseDTO write(PostWriteDTO postCreate, String jwtToken) {
    // jwt -> userId get -> userId 를 찾는건데,userId 를 바로 집어넣으려면 어떻게하나요? (고민)
    Long userId = tokenUtility.getUserIdFromToken(jwtToken);
    User user = entityFinder.findUserById(userId);

    // 예외 발생시
    Category category = entityFinder.findCategoryById(postCreate.getCategoryId());

    // Post 엔티티 생성
    Post newPost =
        Post.builder()
            .user(user)
            .category(category)
            .title(postCreate.getTitle())
            .content(postCreate.getContent())
            .build();

    return new PostResponseDTO(postRepository.save(newPost));
  }

  /** 게시글 수정 */
  public PostResponseDTO edit(Long postId, PostWriteDTO editRequest, String jwtToken) {
    Long userId = tokenUtility.getUserIdFromToken(jwtToken);
    Post post = entityFinder.findPost(postId);

    if (!userId.equals(post.getUser().getId())) {
      throw new SecurityException("해당 게시글을 수정할 권한이 없습니다.");
    }

    Category category = entityFinder.findCategoryById(editRequest.getCategoryId());

    // editRequest, post로 분기처리하고 객체 생성
    PostEditDTO editPost =
        post.toEdit()
            .categoryId(
                editRequest.getCategoryId() != null
                    ? editRequest.getCategoryId()
                    : post.getCategory().getId())
            .title(editRequest.getTitle() != null ? editRequest.getTitle() : post.getTitle())
            .content(
                editRequest.getContent() != null ? editRequest.getContent() : post.getContent())
            .build();

    // post 엔티티 수정
    post.edit(editPost, category);

    // 저장
    return new PostResponseDTO(postRepository.save(post));
  }

  /** 게시글 삭제 - (이미 삭제된 게시글이어도 204) */
  public void delete(Long postId, String jwtToken) {
    // 토큰 -> userId 추출
    Long userId = tokenUtility.getUserIdFromToken(jwtToken);

    // 게시글 조회
    Optional<Post> optionalPost = entityFinder.findPostById(postId);

    // 게시글이 존재할 경우
    if (optionalPost.isPresent()) {
      Post post = optionalPost.get();

      if (userId.equals(post.getUser().getId())) {
        postRepository.deleteById(postId);
      } else {
        throw new SecurityException("해당 게시글을 삭제할 권한이 없습니다.");
      }
    } else {
      // 게시글이 존재하지 않는 경우에도 성공으로 간주
      log.info("게시글 X  이미 삭제 postId={}", postId);
    }
  }

  /** 전체 게시글 조회 - (최근 작성된 순으로) */
  public List<PostResponseDTO> getPostAll(Pageable pageable) {
    return postRepository
        .findAllByOrderByCreatedAtDesc(pageable)
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
        .categoryId(post.getCategory().getId())
        .userId(post.getUser().getId())
        .title(post.getTitle())
        .content(post.getContent())
        .hit(post.getHit())
        .createdAt(post.getCreatedAt())
        .build();
  }

  /** 전체 게시판 내 게시글 검색 */
  public List<PostResponseDTO> searchPost(String keyword, Pageable pageable) {
    return postRepository
        .findByTitleContainingOrContentContainingOrderByCreatedAtDesc(keyword, keyword, pageable)
        .stream()
        .map(PostResponseDTO::new)
        .collect(Collectors.toList());
  }
}
