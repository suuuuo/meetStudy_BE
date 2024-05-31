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

  /* 게시글 작성 */
  public PostResponseDTO write(PostWriteDTO postCreate, String jwtToken) {
    Long userId = tokenUtility.getUserIdFromToken(jwtToken);
    log.info("user name: {}", userId);

    // 예외 발생시
    User user = entityFinder.findUserById(userId);
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

  /* 게시글 수정 */
  public PostResponseDTO edit(Long postId, PostWriteDTO editRequest) {
    // postId로 게시글 찾고 예외 발생시
    Post post = entityFinder.findPostById(postId);

    // 조회된 post로 postEdiorBuilder 생성 (현재 상태를 기반으로 한 빌더 객체)
    PostEditDTO.PostEditDTOBuilder postEditorBuilder = post.toEditor();

    // 수정된 내용(editRequest)으로 postEditor 객체 생성 (title / content 내용이 null 이면 기존 값으로)
    String title = editRequest.getTitle() != null ? editRequest.getTitle() : post.getTitle();
    String content =
        editRequest.getContent() != null ? editRequest.getContent() : post.getContent();

    PostEditDTO editPost = postEditorBuilder.title(title).content(content).build();

    // post 엔티티를 수정
    post.edit(editPost);
    // 수정된 post DB에 저장
    postRepository.save(post);

    return new PostResponseDTO(post);
  }

  /* 게시글 삭제 */
  public void delete(Long postId) {
    postRepository.deleteById(postId);
  }

  /* 전체 게시글 조회 */
  public List<PostResponseDTO> getPostAll(Pageable pageable) {
    return postRepository.findAll(pageable).stream() /* Page<Post> 객체를 스트림으로 변환 */
        .map(PostResponseDTO::new) /* 스트림 내 Post 객체 -> ResponsePostGet 객체로 변환. */
        .collect(Collectors.toList()); /* ResponsePostGet을 리스트로 반환 */
  }

  /* 게시글 상세 조회(postId) - (사용자가 게시글 제목을 클릭했을때) */
  public PostResponseDTO getPost(Long postId) {
    Post post = entityFinder.findPostById(postId);

    // 조회수 증가
    updateHit(postId);

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

  /** 게시글 view -> 게시글 조회수 증가 */
  private void updateHit(Long postId) {
    postRepository.updateHit(postId);
  }

  /* 전체 게시판 내 게시글 검색 */
  public List<PostResponseDTO> searchPost(String keyword) {
    return postRepository.findByTitleContainingOrContentContaining(keyword, keyword).stream()
        .map(PostResponseDTO::new)
        .collect(Collectors.toList());
  }
}
