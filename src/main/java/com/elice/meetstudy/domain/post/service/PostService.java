package com.elice.meetstudy.domain.post.service;

import com.elice.meetstudy.domain.category.entity.Category;
import com.elice.meetstudy.domain.category.repository.CategoryRepository;
import com.elice.meetstudy.domain.post.domain.Post;
import com.elice.meetstudy.domain.post.dto.PostCreate;
import com.elice.meetstudy.domain.post.dto.PostEditor;
import com.elice.meetstudy.domain.post.dto.PostGet;
import com.elice.meetstudy.domain.post.dto.PostEdit;
import com.elice.meetstudy.domain.post.repository.PostRepository;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostService {

  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final CategoryRepository categoryRepository;

  @Autowired
  public PostService(
      PostRepository postRepository,
      UserRepository userRepository,
      CategoryRepository categoryRepository) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
    this.categoryRepository = categoryRepository;
  }

  /* 게시글 작성 */
  public Post write(PostCreate postCreate) {
    // User와 Category를 가져와서 엔티티로 설정
    User user = userRepository.findById(postCreate.getUserId()).orElseThrow();
    Category category =
        categoryRepository
            .findById(postCreate.getCategoryId())
            .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 X."));

    // Post 엔티티 생성
    Post post =
        Post.builder()
            .user(user)
            .category(category)
            .title(postCreate.getTitle())
            .content(postCreate.getContent())
            .build();

    return postRepository.save(post);
  }

  /* 게시글 수정 */
  public void edit(Long id, PostEdit request) {
    // postId로 게시글 찾기
    Post post =
        postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 X."));

    PostEditor.PostEditorBuilder postEditorBuilder = post.toEditor();

    PostEditor postEditor =
        postEditorBuilder.title(request.getTitle()).content(request.getContent()).build();

    post.edit(postEditor);
    postRepository.save(post);
  }

  /* 게시글 삭제 */
  public void delete(Long id) {
    postRepository.deleteById(id);
  }

  /* 전체 게시글 조회 */
  public List<PostGet> getPostAll(Pageable pageable) {
    return postRepository.findAll(pageable).stream() /* Page<Post> 객체를 스트림으로 변환 */
        .map(PostGet::new) /* 스트림 내 Post 객체 -> ResponsePostGet 객체로 변환. */
        .collect(Collectors.toList()); /* ResponsePostGet을 리스트로 반환 */
  }

  /* 특정 게시글 조회(postId) */
  public PostGet getPost(Long postId) {
    Post post =
        postRepository.findById(postId).orElseThrow(IllegalAccessError::new); /* 게시글 없을때 예외처리 */

    return PostGet.builder()
        .id(post.getId())
        .categoryId(post.getCategory().getId())
        .userId(post.getUser().getId())
        .title(post.getTitle())
        .content(post.getContent())
        .hit(post.getHit())
        .createdAt(post.getCreatedAt())
        .build();
  }

  /* 전체 게시판 내 게시글 검색 */
  public List<PostGet> searchPost(String keyword) {
    return postRepository.findByTitleContainingOrContentContaining(keyword, keyword).stream()
        .map(PostGet::new)
        .collect(Collectors.toList());
  }
}
